# Cloud Foundry applications with Spring Cloud

# Introduction

## Requirements
* Install Java 8 JDK
* Install Git client
* Install an IDE of your choice.   
    * [Netbeans](https://netbeans.org/downloads/)
    * [Spring Tool Suite](https://spring.io/tools/sts/all/)    
    * [IntelliJ Idea](https://www.jetbrains.com/idea/download/)
    * [VS Code](https://code.visualstudio.com/)       
* Ensure you have commandline http client installed. Like: 
    * cURL [https://curl.haxx.se/download.html](https://curl.haxx.se/download.html)
    * HTTPie [https://httpie.org/](https://httpie.org/)
* [Install Cloud Foundry CLI](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html)


## Register
Visit [https://run.pivotal.io](https://run.pivotal.io) and register for Pivotal Web Services.
You will have to provide a phone number for completing the registration. 
If you have registered previously and your trial has expired please contact me on [corneil.duplessis@gmail.com](mailto:corneil.duplessis@gmail.com)

# Start Project

## Checkout and Build
```bash
mkdir cf-demo
cd cf-demo
git clone https://github.com/corneil/cf-demo.git
cd cf-demo
chmod a+x ./gradlew
./gradlew build
```

## Login and Deploy

```bash
cf login -a api.run.pivotal.io
cd start 
cf push
# Take note of the random route name provided
# View recent log
cf logs cf-demo --recent
# View log output
cf logs cf-demo 
```

In a new command shell execute http request to view logs

```bash
curl -i http://<route-name>/event?period=1h,1m
# OR
http http://<route-name>/event?period=1h,1m
```
The parameter `period` is optional and denotes the search range in the format:

* y - Years before now
* M - Months before now
* d - Days before now
* h - Hours before now
* m - Minutes before now
* s - Seconds before now

Examples:
* `5M` - From 5 months before until now.
* `5M,1d` -  From 5 months before until 1 day before now.

In command shell execte http POST to create events
```bash
curl -i -X POST http://<route-name>/event/event1
# OR
http POST http://<route-name>/event/event1
```

The view the logs to see events or specific event

```bash
curl -i http://<route-name>/event/event1
# OR
http http://<route-name>/event/event1
```

# Tasks

## Relational Database Support

### Spring Data Repository
Add database support by implementing a Spring Data Repository for saving and retrieving events from a relational database.

### Create database service

```bash
cf marketplace -s elephantsql
# Create a service instance with the free plan:
cf create-service elephantsql turtle cf-demo-db
# Bind the newly created service to the app:
cf bind-service cf-demo cf-demo-db
# Build application while in `start`
../gradlew assemble
# Deploy update
cf push
```

## Message Queue Support

### AMQP Listener
Implement a listener that will receive an event on a queue and write it to the database. Change service to send event to the queue.  
Hint:
    Send messages with `org.springframework.amqp.core.AmqpTemplate`
    `org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer` is sufficient listener. 

### RabbitMQ Configuration
Provide configuration for Queue, TopicExchange, Binding, MessageListenerContainer and MessageListenerAdapter

### Create message queue service

```bash
cf marketplace -s cloudamqp
# Create a CloudAMQP service instance for the free plan:
cf create-service cloudamqp lemur cf-demo-mq
# Bind new service to application 
cf bind-service cf-demo cf-demo-mq
# Deploy new version of application
cf push
```

