package ru.mahotin.util;

import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;

public class LogUtil {
     public void logger(LogLevel logLevel, Logger log, String msg) {
         switch (logLevel) {
             case INFO -> log.info(msg);
             case TRACE -> log.trace(msg);
             case DEBUG -> log.debug(msg);
             case WARN -> log.warn(msg);
             case ERROR -> log.error(msg);
         }
    }
}