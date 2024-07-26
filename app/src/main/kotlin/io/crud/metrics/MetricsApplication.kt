package io.crud.metrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class MetricsApplication

fun main(args: Array<String>) {
	runApplication<MetricsApplication>(*args)
}