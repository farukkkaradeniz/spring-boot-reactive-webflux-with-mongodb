# spring-boot-reactive-webflux-with-mongodb
Spring Boot Reactive Web Flux With Mongo Db

# Overview
This application contains an example for sprin boot reactive web flux with reactive mongodb.

# Integration

First of all we need to add required dependencies in pom.xml file.

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-test</artifactId>
    <scope>test</scope>
</dependency>
```

After adding dependencies, now we need to configure application.yml file for connecting mongoDb.

```yaml
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: reactiveSpringExample
      username: blacksea
      password: blacksea
      authentication-database: admin
```

In application you can find unit tests for how to manage web flux via webtestclient.

# Building Containers
If you already installed docker just run `$ docker-compose build` command.

# Running Containers

When Containers builded successfully then you can start containers with ``$ docker-compose up`` command.

http://localhost:1453/items is a reactive streaming endpoint. For subscription you need to add one data. 

After added any data with calling post method http://localhost:1453/items with ItemDocument object refresh the http://localhost:1453/items endpoint on web browser. 

Now you are starting to listen reactive mongo db repository with Tailable Cursor. Send any post method to save endpoint you can see the new data in items endpoint without refreshing screen.

That's all. 

###### Contact

For any question;

Mail : karadenizfaruk28@gmail.com

Linkedin : https://www.linkedin.com/in/faruk-karadeniz/

