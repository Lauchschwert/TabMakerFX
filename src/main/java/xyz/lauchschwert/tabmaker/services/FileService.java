package xyz.lauchschwert.tabmaker.services;

import java.io.File;

public class FileService {

    public static boolean isValidReadableFile(File file) {
        return file != null && file.exists() && file.isFile() && file.canRead();
    }

    public static boolean isValidWritableFile(File file) {
        return file != null && file.exists() && file.isFile() && file.canWrite();
    }

    public static boolean isValidDirectory(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

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
}