package com.github.corneil.cloudfoundry.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@Profile("!cloud")
@EnableReactiveMongoRepositories(basePackages = "com.github.corneil.cloudfoundry.demo.persistence")
@Slf4j
public class LocalConfig {
    @Bean
    public Queue queue() {
        log.info("queue");
        return new Queue("cf-demo-queue", true);
    }
}
