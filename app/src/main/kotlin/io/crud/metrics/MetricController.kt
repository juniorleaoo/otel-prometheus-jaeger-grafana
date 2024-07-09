package io.crud.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.Timer
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import io.micrometer.registry.otlp.OtlpMeterRegistry
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/metrics")
class MetricController(
    private val observationRegistry: ObservationRegistry,
    private val otlpMeterRegistry: OtlpMeterRegistry,
) {

    @PostMapping("/counter")
    fun counter(): ResponseEntity<Void> {
        Counter.builder("metrics_counter")
            .register(otlpMeterRegistry)
            .increment()
        return ResponseEntity.ok().build()
    }

    @PostMapping("/gauge")
    fun gauge(): ResponseEntity<Void> {
        val user = object {
            val name = "John"
            val age = 30
        }

        Gauge.builder("metrics_gauge", user) { it.age.toDouble() }
            .register(otlpMeterRegistry)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/timer")
    fun timer(): ResponseEntity<Void> {
        Timer.builder("metrics_time")
            .register(otlpMeterRegistry)
            .record(1000, java.util.concurrent.TimeUnit.MILLISECONDS)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/summary")
    fun summary(): ResponseEntity<Void> {
        DistributionSummary.builder("metrics_summary")
            .register(otlpMeterRegistry)
            .record(1000.0)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/observation")
    fun observation(): ResponseEntity<Void> {
        Observation.createNotStarted("metrics_observation", observationRegistry)
            .lowCardinalityKeyValue("status", "ACTIVE")
            .highCardinalityKeyValue("publicId", UUID.randomUUID().toString())
            .observe {
                println("observation started")
            }

        return ResponseEntity.ok().build()
    }


}