package com.github.corneil.cloud_foundry.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig extends AbstractCloudConfig {
    @Bean
    public ConnectionFactory amqpConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory();
    }

    @Bean
    public Queue queue() {
        return new Queue("cf-demo-queue", true);
    }
}
