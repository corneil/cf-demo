package com.github.corneil.cloud_foundry.demo.config;


import com.github.corneil.cloud_foundry.demo.service.EventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"cloud", "cloudamqp", "amqp", "rabbitmq"})
@Slf4j
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        log.info("queue:{}", MessageQueueNames.QUEUE_NAME);
        return new Queue(MessageQueueNames.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        log.info("exchange:{}", MessageQueueNames.TOPIC_EXCHANGE_NAME);
        return new TopicExchange(MessageQueueNames.TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        log.info("binding:{}", MessageQueueNames.ROUTING_KEY);
        return BindingBuilder.bind(queue).to(exchange).with(MessageQueueNames.ROUTING_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        log.info("container");
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MessageQueueNames.QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(EventListener eventListener) {
        log.info("listenerAdapter");
        return new MessageListenerAdapter(eventListener, "receiveMessage");
    }
}
