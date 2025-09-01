package xyz.lauchschwert.tabmaker.handler;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.lauchschwert.tabmaker.core.enums.InstrumentType;
import xyz.lauchschwert.tabmaker.core.ex.ExportException;
import xyz.lauchschwert.tabmaker.core.ex.ImportException;
import xyz.lauchschwert.tabmaker.core.ui.components.panels.InstrumentPanel;
import xyz.lauchschwert.tabmaker.core.services.importexport.TmImportExportService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

class ImportExportServiceTest {
    private TmImportExportService service;

    @TempDir
    Path tempDir;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    void setUp() {
        try (MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class)) {
            pathsMock.when(() -> Paths.get(anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(tempDir.resolve("test-saves"));
            service = new TmImportExportService();
        }
    }

    @Test
    void handleImport_validJSONFile_emptyNotes_returnsInstrumentPanel() throws Exception, ImportException {
        final String json = """
                {
                  "instrumentType": "GUITAR",
                  "tabPanels": [
                    {
                      "string": "E",
                      "notes": []
                    }
                  ]
                }
                """;

        File testFile = tempDir.resolve("test.json").toFile();
        Files.writeString(testFile.toPath(), json);

        InstrumentPanel result = service.handleInstrumentPanelImport(testFile);
        assertThat(result).isNotNull();
        assertThat(result.getInstrumentType()).isEqualTo(InstrumentType.GUITAR);
        assertThat(result.getTabPanels().get(0).getStringName()).isEqualTo("E");
    }

    @Test
    void handleImport_validJson_withNotes_returnsInstrumentPanel() throws Exception, ImportException {
        // 3 notes
        final String json = """
                {
                  "instrumentType": "GUITAR",
                  "tabPanels": [
                    {
                      "string": "E",
                      "notes": [
                        "1",
                        "2",
                        "3"
                      ]
                    }
                  ]
                }
                """;

        File testFile = tempDir.resolve("test.json").toFile();
        Files.writeString(testFile.toPath(), json);
        InstrumentPanel result = service.handleInstrumentPanelImport(testFile);

        assertThat(result).isNotNull();
        assertThat(result.getTabPanels().get(0).getNotes()).hasSize(3);
    }

    @Test
    void handleImport_malformedJSON_throwsImportException() throws IOException {
        // no Instrument Type
        final String json = """
                {
                  "instrumentType": "GUITAR",
                  "tabPanels": [
                    {
                      "string": ,
                      "notes": [
                        ""
                      ]
                    }
                  ]
                }
                """;

        File testFile = tempDir.resolve("test.json").toFile();
        Files.writeString(testFile.toPath(), json);

        assertThrows(ImportException.class, () -> service.handleInstrumentPanelImport(testFile));
    }

    @Test
    void handleImport_validJSON_noNotes_throwsMalformedJsonException() throws IOException {
        final String json = """
                        {
                          "instrumentType": "GUITAR",
                          "tabPanels": [
                            {
                              "string": "E",
                            }
                          ]
                        }
                """;
        File testFile = tempDir.resolve("test.json").toFile();
        Files.writeString(testFile.toPath(), json);

        assertThrows(ImportException.class, () -> service.handleInstrumentPanelImport(testFile));
    }

    @Test
    void handleExport_instrumentPanel_canImportViaExportFile() throws ImportException, ExportException {
        InstrumentPanel guitarPanel = new InstrumentPanel(InstrumentType.GUITAR);

        final File destination = tempDir.resolve("save.json").toFile();

        service.handleInstrumentPanelExport(destination, guitarPanel);
        assertThat(destination.exists()).isTrue();

        InstrumentPanel importedPanel = service.handleInstrumentPanelImport(destination);
        assertThat(importedPanel).isNotNull();
    }

    @Test
    void handleExport_instrumentPanelNull_fails() {
        assertThrows(ExportException.class, () -> service.handleInstrumentPanelExport(new File(""), null));
    }
}
