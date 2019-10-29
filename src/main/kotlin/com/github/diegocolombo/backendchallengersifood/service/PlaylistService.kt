package com.github.diegocolombo.backendchallengersifood.service

import com.github.diegocolombo.backendchallengersifood.gateway.OpenWeatherGateway
import com.github.diegocolombo.backendchallengersifood.gateway.SpotifyGateway
import org.springframework.stereotype.Service

@Service
class PlaylistService {

    fun getPlaylistByCity(city: String): List<String> {
        return getGenre(OpenWeatherGateway.getTemperatureByCity(city))
                .let { SpotifyGateway.getPlaylistByGenre(it) }
    }

    fun getPlaylistByCoordinates(lat: Double, lon: Double): List<String> {
        return getGenre(OpenWeatherGateway.getTemperatureByCoordinate(lat, lon))
                .let { SpotifyGateway.getPlaylistByGenre(it) }
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