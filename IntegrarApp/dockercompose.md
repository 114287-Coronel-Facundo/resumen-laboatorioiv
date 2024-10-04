# Integrar Otra App

<p>
    <em>Primer paso para realizar la integracion de otra app.</em>
</p>


<em>Armar el docker-compose, dejo uno de ejemplo. Rescataria que es importante que pongamos la URL_API_DATOS ya que seria el enlace directo a nuestra api asi lo podemos hacer por inyeccion y queda mucho mejor y mas arreglable en caso de errores de tipeo.</em>

```yml
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      URL_API_DATOS: http://app-datos:8080
    ports:
      - "8081:8081"
    networks:
      - prueba
    depends_on:
      - app-datos

  app-datos:
    image: tupfrcutn/elecciones-2023:2.0.0
    ports:
      - "8080:8080"
    networks:
      - prueba

networks:
  prueba:

```
