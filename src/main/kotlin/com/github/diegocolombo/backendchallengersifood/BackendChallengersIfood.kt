package com.github.diegocolombo.backendchallengersifood

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker

@EnableCircuitBreaker
@SpringBootApplication
class BackendChallengersIfood

fun main(args: Array<String>) {
	runApplication<BackendChallengersIfood>(*args)
}
