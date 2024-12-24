package ru.mahotin.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.mahotin.aspect.AspectLogHttpRequestParam;
import ru.mahotin.aspect.TaskAspect;
import ru.mahotin.aspect.TaskAspectSortingByOrder;
import ru.mahotin.util.LogUtil;

@Configuration
@EnableConfigurationProperties(CustomProperties.class)
@ConditionalOnClass(CustomProperties.class)
@ConditionalOnProperty(prefix = "logger", name = "enable", havingValue = "true")
public class ConfigClass {

    @Order(1)
    @Bean
    TaskAspect taskAspect() {
        return new TaskAspect(logUtil());
    }
    @Order(2)
    @Bean
    TaskAspectSortingByOrder taskAspectSortingByOrder() {
        return new TaskAspectSortingByOrder();
    }

    @Bean
    LogUtil logUtil() {
        return new LogUtil();
    }

    @Bean
    AspectLogHttpRequestParam aspectLogHttpRequestParam() {
        return new AspectLogHttpRequestParam(logUtil());
    }

    @PostConstruct
    public void init() {
        System.out.println("Beans os ok");
    }

}
