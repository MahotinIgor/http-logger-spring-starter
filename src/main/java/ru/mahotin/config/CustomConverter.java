package ru.mahotin.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CustomConverter implements Converter<String, LoggingLevel> {
        @Override
        public LoggingLevel convert(String from) {
            return new LoggingLevel(LogLevel.valueOf(from));
        }
}