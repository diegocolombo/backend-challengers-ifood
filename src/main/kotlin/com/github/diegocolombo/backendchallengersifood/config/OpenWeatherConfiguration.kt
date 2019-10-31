package com.github.diegocolombo.backendchallengersifood.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "app.openweather")
class OpenWeatherConfiguration {

    lateinit var appId: String
    lateinit var url: String

}