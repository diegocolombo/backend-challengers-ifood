package com.github.diegocolombo.backendchallengersifood.gateway

import com.github.diegocolombo.backendchallengersifood.config.OpenWeatherConfiguration
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import khttp.get
import khttp.responses.Response
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class OpenWeatherGateway(val config: OpenWeatherConfiguration) {

    @HystrixCommand(fallbackMethod = "fallback")
    fun getTemperatureByCity(city: String): Double {
        return extractTemperature(get(config.url, params = mapOf(
                "q" to city,
                "appid" to config.appId,
                "units" to "metric"
        )))
    }

    @HystrixCommand(fallbackMethod = "fallback")
    fun getTemperatureByCoordinate(lat: Double, lon: Double): Double {
        return extractTemperature(get(config.url, params = mapOf(
                "lat" to lat.toString(),
                "lon" to lon.toString(),
                "appid" to config.appId,
                "units" to "metric"
        )))
    }

    fun fallback(str: String): Double = randomTemp()

    fun fallback(lat: Double, lon: Double): Double = randomTemp()

    private fun randomTemp(): Double = Random.nextDouble(-20.0, 40.0)

    private fun extractTemperature(response: Response): Double {
        return response
                .jsonObject
                .getJSONObject("main")
                .getBigDecimal("temp")
                .toDouble()
    }

}