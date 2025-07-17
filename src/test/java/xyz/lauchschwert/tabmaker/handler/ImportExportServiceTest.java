package xyz.lauchschwert.tabmaker.handler;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import xyz.lauchschwert.tabmaker.exceptions.ImportException;
import xyz.lauchschwert.tabmaker.ui.panels.instrumentpanels.base.InstrumentPanel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

class ImportExportServiceTest {
    private ImportExportService service;

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
            service = new ImportExportService();
        }
    }

    @Test
    void handleImport_validJSONFile_emptyTabPanelNotes_returnsInstrumentPanel() throws Exception, ImportException {
        final String json = """
                {
                  "tabPanels": [
                    {
                      "string": "E",
                      "notes": [
                        ""
                      ]
                    }
                  ]
                }
                """;

        File testFile = tempDir.resolve("test.json").toFile();
        Files.writeString(testFile.toPath(), json);

        InstrumentPanel result = service.handleImport(testFile);
        assertThat(result.getTabPanels().get(0).getStringName()).isEqualTo("E");
        assertThat(result).isNotNull();
    }

    @Test
    void handleImport_validJson_withNotes_returnsInstrumentPanel() throws Exception, ImportException {
        final String json = """
                {
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
        InstrumentPanel result = service.handleImport(testFile);

        assertThat(result.getTabPanels().get(0).getStringName()).isEqualTo("E");
        assertThat(result.getTabPanels().get(0).getNotes()).hasSize(3);
    }

    @Test
    void handleImport_malformedJSON_throwsImportException() throws Exception {
        final String json = """
                {
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

        assertThrows(ImportException.class, () -> service.handleImport(testFile));
    }

    @Test
    void handleImport_validJSON_noNotes_throwsMalformedJsonException() throws IOException {
        final String json = """
                        {
                          "tabPanels": [
                            {
                              "string": "E",
                            }
                          ]
                        }
                """;
        File testFile = tempDir.resolve("test.json").toFile();
        Files.writeString(testFile.toPath(), json);

        assertThrows(ImportException.class, () -> service.handleImport(testFile));
    }

    @Test
    void handleExport_mockedInstrumentPanel_canImportViaExportFile() throws ImportException {
        InstrumentPanel instrumentPanel = Mockito.mock(InstrumentPanel.class);

        instrumentPanel.createTabPanel(0);

        final File destination = tempDir.resolve("save.json").toFile();

        service.handleExport(destination, instrumentPanel);
        assertThat(destination.exists()).isTrue();

        InstrumentPanel importedPanel = service.handleImport(destination);
        assertThat(importedPanel).isNotNull();
    }
}
