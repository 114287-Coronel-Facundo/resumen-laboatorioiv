# Integrar DB

<p>
    <em>Segundo paso para realizar la integracion de una DB.</em>
</p>


<em>Armar el docker-compose, dejo uno de ejemplo. En caso de tener dudas buscar en el dockerhub en el apartado de la DB a integrar y ver la parte de Environment Variables y Where to Store Data.</em>
```yml
services:
  mysql:
    container_name: mysql
    image: mysql:8.0
    environment:
      MYSQL_DATABASE: REEMPLAZAR
      MYSQL_USER: REEMPLAZAR
      MYSQL_PASSWORD: REEMPLAZAR
      MYSQL_ROOT_PASSWORD: rootpassword
    ports:
      - "3306:3306"
    volumes:1
      - mysql_data:/var/lib/mysql
    networks:
      - parcial-network

  app:
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/REEMPLAZAR
      SPRING_DATASOURCE_USERNAME: REEMPLAZAR
      SPRING_DATASOURCE_PASSWORD: REEMPLAZAR
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    networks:
      - parcial-network

volumes:
  mysql_data:

networks:
  parcial-network:

```

### Importante:
- Los datos de **SPRING_DATASOURCE_USERNAME** y **MYSQL_USER** tienen que ser iguales.
- Los datos de **SPRING_DATASOURCE_PASSWORD** y **MYSQL_PASSWORD** tienen que ser iguales.
- El valor de **SPRING_DATASOURCE_URL** donde dice exclusivamente REEMPLAZAR debe ser reemplazado por el nombre de la base de datos, que también tiene que ser igual a **MYSQL_DATABASE**.
- La network no es necesaria, pero yo la pongo por las dudas.
