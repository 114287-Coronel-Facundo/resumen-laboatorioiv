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




application.properties.
server.port=8081
EXTERNAL_API_URL=${EXTERNAL_API_URL