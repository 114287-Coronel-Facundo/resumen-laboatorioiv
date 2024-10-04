# Rest Client

<p>
    <em>Dejo un rest client generico para copiar y pegar. Lo que este en rojo lo vas a tener que reemplazar, ya que son las variables de el rest client que pegue.</em>
</p>


```java
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FakeWeatherRestClient {
    @Autowired
    private RestTemplate restTemplate;

    private static final String RESILIENCE4J_INSTANCE_NAME = "circuitBreakerParcial";
    private static final String FALLBACK_METHOD = "fallback";

    public ResponseEntity<String> fallback(Exception ex) {
        return ResponseEntity.status(503).body("Response from Circuit Breaker.");
    }

    @Value("${variable.de.entorno}")
    private static final String HOST;;

    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<List<LocationDto>> getAllLocation() {
        return ResponseEntity.ok(Arrays.asList(restTemplate.getForObject(HOST + "/location", LocationDto[].class)));
    }

    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<LocationDto> getLocationById(Long id) {
        LocationDto location = restTemplate.getForObject(HOST + "/location/" + id, LocationDto.class);
        return ResponseEntity.ok(location);
    }
}
```
