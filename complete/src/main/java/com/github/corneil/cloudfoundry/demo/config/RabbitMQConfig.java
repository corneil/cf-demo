package com.github.corneil.cloudfoundry.demo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile({"cloud", "rabbitmq"})
@Slf4j
public class RabbitMQConfig extends AbstractCloudConfig {

    @Bean
    public Queue queue() {
        return new Queue("cf-demo-queue", true);
    }

    @Bean
    public ConnectionFactory amqpConnectionFactory() {
        return connectionFactory().rabbitConnectionFactory();
    }

}

