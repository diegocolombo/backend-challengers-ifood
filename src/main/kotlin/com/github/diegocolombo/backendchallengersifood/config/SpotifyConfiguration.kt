package com.github.diegocolombo.backendchallengersifood.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.spotify")
class SpotifyConfiguration {

    lateinit var url: String
    lateinit var authUrl: String
    lateinit var clientId: String
    lateinit var secretKey: String

}