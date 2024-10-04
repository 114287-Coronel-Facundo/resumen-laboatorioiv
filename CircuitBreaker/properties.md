# Circuit Breaker

<p>
    <em>Segundo paso configurar el CB en application.properties.</em>
</p>


```properties
# El umbral de tasa de fallos. Si el 50% de las llamadas fallan, el CB se abrira.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.failure-rate-threshold=50
# El umbral de llamadas lentas. Si el 50% de las llamadas son lentas, se considerarán como fallos.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.slow-call-rate-threshold=50
# Define el tiempo que una llamada debe tardar en completarse para ser considerada como lenta, en milisegundos.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.slow-call-duration-threshold=30000
# Número de llamadas permitidas cuando el CB está en estado half-open.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.permitted-number-of-calls-in-half-open-state=1
# Define el tipo de ventana deslizante que se utilizara.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.sliding-window-type=count_based
# Tamaño de la ventana deslizante en cantidad de llamadas. 
resilience4j.circuitbreaker.instances.circuitBreakerParcial.sliding-window-size=1
# Numero minimo de llamadas que se deben realizar antes de que el CB empiece a calcular el estado abierto o cerrado.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.minimum-number-of-calls=5
# Duracionen milisegundos que el CB permanecera en estado abierto antes de pasar a half-open.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.wait-duration-in-open-state=300000
# Habilita la transición automatica de estado abierto a half-open una vez que el tiempo de espera ha pasado.
resilience4j.circuitbreaker.instances.circuitBreakerParcial.automatic-transition-from-open-to-half-open-enabled=true
# Maxima duracion que el CB permanecera en estado half-open antes de cerrarse nuevamente si no hay fallos.
resilience4j.circuitbreaker.instances.circuitbreakerparcial.max-wait-duration-in-half-open-state=30s
```
