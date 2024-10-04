# Integrar DB

<p>
    <em>Tercer paso para realizar la integracion de una DB es crear un perfil. Es importante darles el mismo nombre que indico, estaran en negrita.</em>
</p>


<em>Copiar, pegar y reemplazar directo. Este es el **application.properties**.</em>
```properties
#### Spring Aplication Name ####
spring.application.name=REEMPLAZAR

##### Application properties #####
app.name='@project.name@'
app.desc='@project.description@'
app.version='@project.version@'
app.url=http://localhost:8080
app.dev-name=UTN_TPI_2W2_02
app.dev-mail=none@none.com

#####  PORT #####
server.port=8080

### Profile for DB enviroment ###
# El perfil dev va a tener la base H2 y el prod va a tener la base posta.
# Values: dev | prod
spring.profiles.active=dev


#####  SPRING DOC PROPERTIES #####
springdoc.packages-to-scan=REEMPLAZAR CARPETA DE CONTROLLERS
springdoc.show-actuator=true
springdoc.swagger-ui.disable-swagger-default-url=true
# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html

# Values: TRACE | DEBUG | WARN | INFO
logging.level.root=WARN

# Disable spring.jpa.open-in-view
spring.jpa.open-in-view=false
```

<em>Copiar y pegar directo. Este es el **application-dev.properties**.</em>
```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.defer-datasource-initialization=true

# Config for data.sql
spring.sql.init.mode=embedded
```

<em>Copiar y pegar directo. Este es el **application-prod.properties**. Las inyecciones se hacen mediante el docker-compose.yml</em>
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true

spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.connection-timeout=30000

# Config for data.sql
spring.sql.init.mode=never
#spring.sql.init.data-locations=data.sql

```
