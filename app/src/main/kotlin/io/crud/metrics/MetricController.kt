package io.crud.metrics

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.metrics.LongCounter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/metrics")
class MetricController(
    val openTelemetry: OpenTelemetry
) {

    @PostMapping("/counter")
    fun counter(): ResponseEntity<Void> {
        val meter = openTelemetry.getMeter("application")

        val counter: LongCounter = meter.counterBuilder("custom-metric-counter")
            .setDescription("Métrica de contagem")
            .setUnit("conta-uai")
            .build()

        counter.add(1, Attributes.of(AttributeKey.stringKey("custom-key"), "custom-value"))

        return ResponseEntity.ok().build()
    }

    @PostMapping("/histogram/{valor}/long")
    fun histogramOfLongs(@PathVariable("valor") valor: Long): ResponseEntity<Void> {
        val meter = openTelemetry.getMeter("application")

        val histogram = meter.histogramBuilder("custom-metric-histogram-long")
            .ofLongs() //não é 100% pq ele faz um (double) value, um cast de long para double, por baixo está usando um DoubleExplicitBucketHistogramAggregator
            .setDescription("Métrica de histograma")
            .setExplicitBucketBoundariesAdvice(listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L))
            .setUnit("uai-long")
            .build()

        Thread.sleep(1000)
        histogram.record(valor)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/histogram/{valor}/double")
    fun histogramOfDoubles(@PathVariable("valor") valor: Double): ResponseEntity<Void> {
        val meter = openTelemetry.getMeter("application")

        val histogram = meter.histogramBuilder("custom-metric-histogram-double")
            .setDescription("Métrica de histograma")
            .setExplicitBucketBoundariesAdvice(listOf(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7))
            .setUnit("uai-double")
            .build()

        histogram.record(valor)

        return ResponseEntity.ok().build()
    }

}