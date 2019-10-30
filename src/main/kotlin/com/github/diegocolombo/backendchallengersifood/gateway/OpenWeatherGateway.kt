package com.github.diegocolombo.backendchallengersifood.gateway

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import khttp.get
import khttp.responses.Response
import org.springframework.stereotype.Component

@Component
class OpenWeatherGateway {

    @HystrixCommand(fallbackMethod = "reliable")
    fun getTemperatureByCity(city: String): Double {
        return extractTemperature(get(URL, params = mapOf(
                "q" to city,
                "appid" to Companion.APP_ID,
                "units" to "metric"
        )))
    }

    @HystrixCommand(fallbackMethod = "reliable")
    fun getTemperatureByCoordinate(lat: Double, lon: Double): Double {
        return extractTemperature(get(URL, params = mapOf(
                "lat" to lat.toString(),
                "lon" to lon.toString(),
                "appid" to Companion.APP_ID,
                "units" to "metric"
        )))
    }

    fun reliable(str: String): Double = 20.0

    fun reliable(lat: Double, lon: Double): Double = 20.0

    private fun extractTemperature(response: Response): Double {
        return response
                .jsonObject
                .getJSONObject("main")
                .getBigDecimal("temp")
                .toDouble()
    }

    companion object {
        private const val URL: String = "http://api.openweathermap.org/data/2.5/weather"
        private const val APP_ID: String = "b77e07f479efe92156376a8b07640ced"
    }

}