package xyz.lauchschwert.tabmaker.core.services;

import java.io.File;
import java.nio.file.Path;

public class TmFileService implements FileService {
    public static void validateFileForReading(File file) throws IllegalArgumentException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + file.getPath());
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Path is not a file: " + file.getPath());
        }
        if (!file.canRead()) {
            throw new IllegalArgumentException("File is not readable: " + file.getPath());
        }
    }

    @Override
    public File getExistingFile(String path) {
        if (path == null || path.trim().isEmpty()) {
            return null;
        }

        final File targetFile = new File(path);
        try {
            if (!targetFile.exists()) {
                return null;
            }
        } catch (SecurityException e) {
            return null;
        }

        return targetFile;
    }

    @Override
    public File getExistingFile(Path path) {
        if (path == null) {
            return null;
        }

        final File targetFile = path.toFile();
        try {
            if (!targetFile.exists()) {
                return null;
            }
        } catch (SecurityException e) {
            return null;
        }

        return targetFile;
    }

    @Override
    public boolean isReadable(File file) {
        try {
            if (file.exists() && file.canRead()) {
                return true;
            }
        } catch (SecurityException e) {
            return false;
        }
        return false;
    }
}