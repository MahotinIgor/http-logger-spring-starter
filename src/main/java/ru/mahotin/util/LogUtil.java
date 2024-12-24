package ru.mahotin.util;

import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;

public class LogUtil {
     public void changeLoggingLevel(LogLevel logLevel, Class obj, Logger log) {
        LoggingSystem system = LoggingSystem.get(obj.getClassLoader());
        system.setLogLevel(log.getName(), logLevel);

    }
}
