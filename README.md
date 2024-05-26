# OpenTelemetry + Jaeger + Prometheus + Grafana

```mermaid
flowchart TD
    A[App] -->B[OpenTelemetry Collector]
    B -->C[Prometheus]
    B -->D[Jaeger]
    C -->E[Grafana]
    D -->E[Grafana]
```


## Getting Started

```shell
./gradlew clean build
```
```shell
docker compose up
```

## Grafana
- Access Grafana at `http://localhost:3000`

## Prometheus
- Access Prometheus UI at `http://localhost:9090`

## Jaeger
- Access Jaeger UI at `http://localhost:16686`

## OpenTelemetry Collector

### Ports
- 1888: pprof extension
- 8888: Prometheus metrics exposed by the collector
- 8889: 
- 13133: health_check extension 
- 4317: gRPC receiver
- 4318: HTTP receiver
- 55678: zpages extension

## APP's

- user-11
  - Java 11
  - Spring Boot Webflux without coroutines
  - Spring Data JPA
- user-11-webflux-coroutines
  - Java 11
  - Spring Boot Webflux with coroutines
  - Spring Data JPA
- user-11-mvc
  - Java 11
  - Spring Boot MVC
  - Spring Data JPA
- user-21-mvc
  - Java 21
  - Spring Boot MVC
  - Spring Data JPA
- user-21-webflux
  - Java 21
  - Spring Boot Webflux
  - Spring Data JPA