package io.crud.user11mvc.config

import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.composite.CompositeMeterRegistry
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler
import io.micrometer.observation.ObservationRegistry
import io.micrometer.registry.otlp.OtlpConfig
import io.micrometer.registry.otlp.OtlpMeterRegistry
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment


@Configuration
class ObservationConfig {

    @Bean
    fun obversationRegistry(meter: OtlpMeterRegistry): ObservationRegistry {
        val registry = ObservationRegistry.create()
        registry.observationConfig()
            .observationHandler(DefaultMeterObservationHandler(meter))
        return registry
    }

    @Bean
    fun loggingRegistry(otlpMeterRegistry: OtlpMeterRegistry): MeterRegistry {
        return CompositeMeterRegistry().add(otlpMeterRegistry)
    }

    @Bean
    fun observationRegistry(environment: ConfigurableEnvironment): OtlpMeterRegistry {
        val config = OtlpConfig(environment::getProperty)
        return OtlpMeterRegistry(config, Clock.SYSTEM)
    }

    @Bean
    fun configure(): MeterRegistryCustomizer<MeterRegistry> {
        return MeterRegistryCustomizer { registry ->
            registry.config()
                .meterFilter(MeterFilter.denyNameStartsWith("jvm"))
                .commonTags("application", "user-11-mvc")
        }
    }

}