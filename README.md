# gateway-login
spring-gateway实现登录验证，并过滤器处理转发请求

## spring-gateway实现登录验证，并过滤器处理转发请求

 1. 核心依赖：

```xml
<properties>
   <java.version>1.8</java.version>
   <spring-cloud.version>Hoxton.SR7</spring-cloud.version>
</properties>
```

```xml
 <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-gateway</artifactId>
 </dependency>
```

2. YAML相关配置：

```yaml
server:
  servlet:
    context-path: /test
  port: 81

spring:
  cloud:
    gateway:
      routes:
#        - id: first_route
#          uri: lb:ws://localhost:81/
#          predicates:
#            - Path=/devSecOps/**
        - id: two_route
#          uri: http://localhost:81/
          uri:   http://localhost:82/devSecOps/
          predicates:
            - Path=/devSecOps/**
          filters:
            - AddRequestHeader=NAME, yinjihuan

  datasource:
    #    url: jdbc:mysql://localhost:3306/ssdlc
    url: jdbc:mysql://10.0.1.100:3306/ssdlc
    username: root
    password: seczone@123
    #    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  servlet:
    multipart:
      max-file-size: 500MB
      max-request-size: 500MB

  main:
    allow-bean-definition-overriding: on

mybatis:
  mapper-locations: classpath:mapper/*.xml

logging:
  level:
    cn.seczone.ssdlc.gateway.*: debug


# custom config variable
loginFailureUrl: /loginFailure
loginSuccessUrl: /loginSuccess
logoutSuccessUrl: /logoutSuccess

##忽略的路径，不走转发，访问本地接口,优先于permitAll
ignore-urls: /login,/401,/logout

```
