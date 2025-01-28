package ru.mahotin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.mahotin.aspect.AspectLogHttpRequestParam;
import ru.mahotin.aspect.TaskAspect;
import ru.mahotin.aspect.TaskAspectSortingByOrder;
import ru.mahotin.util.LogUtil;

@Configuration
@EnableConfigurationProperties(CustomProperties.class)

public class ConfigClass {

    private final CustomProperties customProperties;

    public ConfigClass(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    @Order(1)
    @Bean
    @ConditionalOnProperty(prefix = "logger", name = "enable", havingValue = "true")
    TaskAspect taskAspect() {
        return new TaskAspect();
    }

    @Order(2)
    @Bean
    @ConditionalOnProperty(prefix = "logger", name = "enable", havingValue = "true")
    TaskAspectSortingByOrder taskAspectSortingByOrder() {
        return new TaskAspectSortingByOrder();
    }

    @Bean
    AspectLogHttpRequestParam aspectLogHttpRequestParam() {
        LogLevel logLevel = customProperties
                .getLevels()
                .getOrDefault("aspect2", new LoggingLevel(LogLevel.INFO))
                .logLevel();
        return new AspectLogHttpRequestParam(logUtil(),  logLevel);
    }

    @Bean
    LogUtil logUtil() {
        return new LogUtil();
    }
}
