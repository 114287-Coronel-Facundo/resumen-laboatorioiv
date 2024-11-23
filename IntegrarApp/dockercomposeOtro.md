Aservices:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      SPRING_PROFILES_ACTIVE: docker
      EXTERNAL_API_URL: http://external-api:8080/
    ports:
      - "8081:8081"
    depends_on:
      - external-api
    networks:
      - elections
  external-api:
      container_name: external-api
      image: tupfrcutn/elecciones-2023:2.0.0
      ports:
        - "8080:8080"
      networks:
        - elections

networks:
  elections:




##### Application properties #####
app.name='@project.name@'
app.desc='@project.description@'
app.version='@project.version@'
app.url=http://localhost:8081
app.dev-name=None
app.dev-email=none@none.com

#spring.profiles.active=docker

#####  SPRING DOC PROPERTIES #####
springdoc.packages-to-scan=ar.edu.utn.frc.tup.lc.iv.controllers
springdoc.show-actuator=true
springdoc.swagger-ui.disable-swagger-default-url=true
# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html

## Spring Data Source ##
spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

## Spring JPA ##
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true
#logging.level.root=TRACE

EXTERNAL_API_URL=http://localhost:8080/
server.port=8081

y eso va en el application
