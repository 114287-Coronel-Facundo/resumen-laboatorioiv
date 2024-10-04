# Circuit Breaker

<p>
    <em>Tercer y cuarto paso se aplican en los servicios o restClients que tengamos y unicamente en los metodos publicos que tengamos, ya que son los que se comunicaran.</em>
</p>

<em>Copian y pegan esto en su servicio.</em>
```java
private static final String RESILIENCE4J_INSTANCE_NAME = "circuitBreakerParcial";
private static final String FALLBACK_METHOD = "fallback";

public ResponseEntity<String> fallback(Exception ex) {
    return ResponseEntity.status(503).body("Response from Circuit Breaker.");
}
```


<em>Arriba de cada metodo publico ponen esta anotacion.</em>
```java
@CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
```