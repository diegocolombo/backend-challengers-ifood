package com.github.diegocolombo.backendchallengersifood.gateway

import khttp.get
import khttp.responses.Response
import org.springframework.stereotype.Component

@Component
object OpenWeatherGateway {

    private const val URL: String = "http://api.openweathermap.org/data/2.5/weather"
    private const val APP_ID: String = "b77e07f479efe92156376a8b07640ced"

    fun getTemperatureByCity(city: String): Double {
        return extractTemperature(get(URL, params = mapOf(
                "q" to city,
                "appid" to APP_ID,
                "units" to "metric"
        )))
    }

    fun getTemperatureByCoordinate(lat: Double, lon: Double): Double {
        return extractTemperature(get(URL, params = mapOf(
                "lat" to lat.toString(),
                "lon" to lon.toString(),
                "appid" to APP_ID,
                "units" to "metric"
        )))
    }

    private fun extractTemperature(response: Response): Double {
        return response
                .jsonObject
                .getJSONObject("main")
                .getBigDecimal("temp")
                .toDouble()
    }

}