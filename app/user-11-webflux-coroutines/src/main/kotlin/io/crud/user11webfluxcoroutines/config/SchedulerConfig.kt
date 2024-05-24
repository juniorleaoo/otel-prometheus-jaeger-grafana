package io.crud.user11webfluxcoroutines.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors

@Configuration
class SchedulerConfig(
    @Value("\${spring.datasource.hikari.maximum-pool-size}")
    val connectionPoolSize: Int
) {

    @Bean
    fun jdbcScheduler(): Scheduler {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize))
    }

}