package io.crud.user21webflux.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors

@Configuration
class SchedulerConfig(
    @Value("\${spring.datasource.hikari.maximum-pool-size}")
    val connectionPoolSize: Int,
    @Value("\${scheduler.bounded-elastic}")
    val elasticScheduler: Boolean
) {

    @Bean
    fun jdbcScheduler(): Scheduler {
        if (elasticScheduler) {
            return if (!Schedulers.DEFAULT_BOUNDED_ELASTIC_ON_VIRTUAL_THREADS) {
                Schedulers.fromExecutor(Executors.newVirtualThreadPerTaskExecutor())
            } else {
                Schedulers.boundedElastic()
            }
        }
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize))
    }

}