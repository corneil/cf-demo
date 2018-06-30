# Cloud Foundry applications with Spring Cloud

# Introduction

## Reguirements
* Install Java 8 JDK
* Install Git client
* Install an IDE of your choice.   
    * [Spring Tool Suite](https://spring.io/tools/sts/all/)
    * [VS Code](https://code.visualstudio.com/)
    * [IntelliJ Idea](https://www.jetbrains.com/idea/download/)   
* Ensure you have `curl` installed: [https://curl.haxx.se/download.html](https://curl.haxx.se/download.html)
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
```
