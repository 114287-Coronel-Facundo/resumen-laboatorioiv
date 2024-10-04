# Dockerfile

<p>
    <em>Siempre es el mismo, asi que copia y pega directo. Esta a la misma altura del pom.xml</em>
</p>

```Dockerfile
FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
