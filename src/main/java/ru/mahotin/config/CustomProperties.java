package ru.mahotin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.HashMap;

@ConfigurationProperties("logger")

public class CustomProperties {
    public CustomProperties() {
    }
    private HashMap <String, LoggingLevel> levels;

    public HashMap<String, LoggingLevel> getLevels() {
        return levels;
    }

    public void setLevels(HashMap<String, LoggingLevel> levels) {
        this.levels = levels;
    }
}
