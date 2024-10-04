# Circuit Breaker

<p>
    <em>Segundo paso configurar el CB en application.properties.</em>
</p>


```properties
# Si el 25% de las solicitudes fallan, el circuito se abrirá.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.failure-rate-threshold=25
# Si el 50% de las llamadas son lentas, el circuito se abrirá.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.slow-call-rate-threshold=50
# Se considera que una llamada es lenta si tarda más de 30 segundos.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.slow-call-duration-threshold=30000
# Cuando el circuito esté medio abierto, se permitirá 1 llamada para probar si el servicio se ha recuperado.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.permitted-number-of-calls-in-half-open-state=1
# Usa una ventana deslizante basada en la cantidad para medir la tasa de fallos.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.sliding-window-type=count_based
# La ventana deslizante considerará las últimas 10 llamadas.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.sliding-window-size=10
# El circuito no se abrirá hasta que al menos se haya hecho 1 llamada.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.minimum-number-of-calls=1
# El circuito permanecerá abierto durante 300 segundos (5 minutos) antes de pasar al estado medio abierto.
resilience4j.circuitbreaker.configs.circuitBreakerParcial.wait-duration-in-open-state=300000
```
