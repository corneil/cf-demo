package com.github.corneil.cloudfoundry.demo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Profile("cloud")
@Slf4j
@EnableDiscoveryClient
@EnableMongoRepositories(basePackages = "com.github.corneil.cloudfoundry.demo.persistence")
public class CloudConfig extends AbstractCloudConfig {
    @Bean
    public MongoDbFactory mongoDbFactory() {
        return connectionFactory().mongoDbFactory("cf-demo-mongo");
    }

    @Bean
    public Queue queue() {
        log.info("queue");
        return new Queue("cf-demo-queue", true);
    }

    @Bean
    public ConnectionFactory amqpConnectionFactory() {
        log.info("amqpConnectionFactory");
        return connectionFactory().rabbitConnectionFactory("cf-demo-mq");
    }
}
