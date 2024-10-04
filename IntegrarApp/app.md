# Integrar Otra App

<p>
    <em>Segundo paso para realizar la integracion de otra app.</em>
</p>

<em>Tambien hay que recordar que al ser dos apis, localmente se nos va a levantar el 8080 y 8081, pero despues cuando esten en los contenedores si los dejamos dinamicamente van a ir al 8080 cada uno en su respectivo contenedor, asi que le vamos a setear a nuestra app el 8081.</em>
<br><br>

<em>A continuacion dejo el application.properties</em>
```properties
##### Application properties #####
app.name='@project.name@'
app.desc='@project.description@'
app.version='@project.version@'
app.url=http://localhost:8081
app.dev-name=None
app.dev-email=none@none.com
server.port=8081

#####  SPRING DOC PROPERTIES #####
springdoc.packages-to-scan= REEMPLAZAR
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

url.api-datos=${URL_API_DATOS}

#logging.level.root=WARN
```

<em>Otra cosa a recordar es que si corremos en entorno local, la variable url.api-datos, la tenemos que cambiar, en vez de inyeccion seria http://localhost:8081</em>
```properties
url.api-datos=http://localhost:8081
```

<em>En caso de ya haber visto lo del RestClient o RestTemplate, la variable de entorno a inyectar seria url.api-datos. <br>
 Ej: @Value("${url.api-datos}")</em>
