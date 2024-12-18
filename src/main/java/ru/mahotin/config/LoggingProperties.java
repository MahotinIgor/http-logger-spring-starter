package ru.mahotin.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {
    private boolean enabled;
    @PostConstruct
    public void init() {
        System.out.println("Logging properties initialized!");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
