package xyz.lauchschwert.tabmaker.core.services;

import java.io.File;
import java.util.Properties;

public interface ConfigService {
    Properties loadProperties(File file);
}
