package io.crud.metrics

import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ObservationConfig {

    /*@Bean
    fun openTelemetry(): OpenTelemetry {
        return GlobalOpenTelemetry.get()
    }*/

    @Bean
    fun openTelemetry(): OpenTelemetry {
        return AutoConfiguredOpenTelemetrySdk.initialize().openTelemetrySdk
    }

}