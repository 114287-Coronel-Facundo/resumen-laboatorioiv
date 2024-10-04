# Circuit Breaker

<p>
    <em>Primer paso para realizar el circuit breaker es importar el pom.</em>
</p>


<em>Agregar la version de cloud arriba en las properties. Aprox lineas 10<->20</em>
```xml
<properties>
        <java.version>17</java.version>
        <spring-cloud.version>2022.0.4</spring-cloud.version>
</properties>
```


<em>Agregar la dependencia dentro del bloque dependencies.</em>
```xml
<!--    Circuit Breaker    -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-circuitbreaker-resilience4j</artifactId>
</dependency>
```

<em>Agregar esta configuracio dentro del bloque dependencyManagement. Que suele estar depsues del bloque dependencies.</em>
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```