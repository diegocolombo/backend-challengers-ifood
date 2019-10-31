package com.github.diegocolombo.backendchallengersifood.gateway

import com.github.diegocolombo.backendchallengersifood.auth.BearerAuth
import com.github.diegocolombo.backendchallengersifood.config.SpotifyConfiguration
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import khttp.get
import khttp.post
import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class SpotifyGateway(val config: SpotifyConfiguration) {

    @HystrixCommand(fallbackMethod = "fallback")
    fun getPlaylistByGenre(genre: String): List<String> {
        val auth = BearerAuth(login())

        val playlistUri = get("${config.url}/browse/categories/$genre/playlists", auth = auth)
                .jsonObject
                .getJSONObject("playlists")
                .getJSONArray("items")
                .let { it.getJSONObject(Random.nextInt(0, it.length())) }
                .getJSONObject("tracks")
                .getString("href")

        return get(playlistUri, auth = auth)
                .jsonObject
                .getJSONArray("items")
                .map { it as JSONObject }
                .map { it.getJSONObject("track") }
                .map { it.getString("name") }
    }

    private fun login(): String {
        return post(
                url = config.authUrl,
                headers = mapOf("Content-Type" to MediaType.APPLICATION_FORM_URLENCODED_VALUE),
                data = mapOf(
                        "client_id" to config.clientId,
                        "client_secret" to config.secretKey,
                        "grant_type" to "client_credentials"
                ))
                .jsonObject
                .getString("access_token")
    }

    fun fallback(str: String): List<String> = listOf("I Believe in a Thing Called Love")

}