package com.github.diegocolombo.ifoodbackchallenge.gateway

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OpenWeatherGateway {

    private val URL = "http://api.openweathermap.org/data/2.5/weather"
    private val APP_ID = "b77e07f479efe92156376a8b07640ced"

    fun getTemperatureByCity(city: String): Double {
        return RestTemplate().getForObject("$URL?q=$city&appid=$APP_ID&units=metric", Map::class.java)
                .let { it!!["main"] as Map<String, Any> }
                .let {
                    when(val temp = it["temp"]) {
                        is Double -> temp
                        is Int -> temp.toDouble()
                        else -> 0.0
                } }
    }

}