package com.github.diegocolombo.ifoodbackchallenge.service

import com.github.diegocolombo.ifoodbackchallenge.gateway.OpenWeatherGateway
import com.github.diegocolombo.ifoodbackchallenge.gateway.SpotifyGateway
import org.springframework.stereotype.Service

@Service
class PlaylistService(val openWeatherGateway: OpenWeatherGateway,
                      val spotifyGateway: SpotifyGateway) {

    fun getPlaylistByCity(city: String): List<String> {
        return openWeatherGateway.getTemperatureByCity(city)
                .let { getGenre(it) }
                .let { spotifyGateway.getPlaylistByGenre(it) }
    }

    private fun getGenre(temperature: Double): String {
        return when(temperature) {
            in Double.MIN_VALUE..10.0 -> "classical"
            in 10.0..15.0 -> "rock"
            in 15.0..30.0 -> "pop"
            else -> "party"
        }
    }

}