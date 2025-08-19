package xyz.lauchschwert.tabmaker.core.services;

import java.io.File;
import java.nio.file.Path;

public interface FileService {
    File getExistingFile(String path);
    File getExistingFile(Path path);
    boolean isReadable(File file);
}
