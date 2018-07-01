package com.github.corneil.cloud_foundry.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class ConfigReporter {
    @Slf4j
    private static class ProfileReporter {
        Environment environment;

        public ProfileReporter(Environment environment) {
            this.environment = environment;
        }

        @PostConstruct
        public void report() {
            log.info("Profiles:");
            for (String profile : environment.getActiveProfiles()) {
                log.info("\tProfile:{}", profile);
            }
        }
    }

    @Bean
    public ProfileReporter profileReporter(Environment environment) {
        return new ProfileReporter(environment);
    }
}
