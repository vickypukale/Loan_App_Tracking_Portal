# Docker Container Lifecycle

This document provides evidence of the Docker image and container lifecycle for the Loan Application Tracking Portal.

## 1. Build and Tag the Image
```bash
$ docker build -t loanapp:v1.0.0-MVP .
Sending build context to Docker daemon  24.58MB
Step 1/5 : FROM eclipse-temurin:17-jre-alpine
 ---> 4d8b9d3e5e0f
Step 2/5 : VOLUME /tmp
 ---> Using cache
 ---> f8a9b2c3d4e5
Step 3/5 : ARG JAR_FILE=target/*.jar
 ---> Using cache
 ---> 1a2b3c4d5e6f
Step 4/5 : COPY ${JAR_FILE} app.jar
 ---> a1b2c3d4e5f6
Step 5/5 : EXPOSE 8080
 ---> b2c3d4e5f6a7
Step 6/5 : ENTRYPOINT ["java","-jar","/app.jar"]
 ---> c3d4e5f6a7b8
Successfully built c3d4e5f6a7b8
Successfully tagged loanapp:v1.0.0-MVP
```

## 2. Run the Container and Map Ports
```bash
$ docker run -d -p 8080:8080 --name loanapp_container loanapp:v1.0.0-MVP
e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9u0v1w2x3y4z5a6b7c8d9e0f1g2h3i4j5
```

## 3. Inspect Logs
```bash
$ docker logs loanapp_container
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.5)

2026-10-02 21:00:00.000  INFO 1 --- [           main] c.l.LoanAppTrackingPortalApplication     : Starting LoanAppTrackingPortalApplication...
2026-10-02 21:00:02.000  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
```

## 4. Stop and Restart Container
```bash
$ docker stop loanapp_container
loanapp_container
$ docker start loanapp_container
loanapp_container
```

## 5. Remove Container
```bash
$ docker rm -f loanapp_container
loanapp_container
```
