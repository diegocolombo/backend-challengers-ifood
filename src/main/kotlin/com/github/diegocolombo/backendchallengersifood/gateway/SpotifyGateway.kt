package com.github.diegocolombo.backendchallengersifood.gateway

import com.github.diegocolombo.backendchallengersifood.auth.BearerAuth
import khttp.get
import khttp.post
import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
object SpotifyGateway {

    private const val URL: String = "https://api.spotify.com/v1/browse/categories"
    private const val CLIENT_ID: String = "ac6240ec42734f31b3c9940964ce6578"
    private const val CLIENT_SECRET: String = "4aea202fd2e94039bafb7d0695f4310d"

    fun getPlaylistByGenre(genre: String): List<String> {
        val auth = BearerAuth(login())

        val playlistUri = get("$URL/$genre/playlists", auth = auth)
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
                url = "https://accounts.spotify.com/api/token",
                headers = mapOf("Content-Type" to MediaType.APPLICATION_FORM_URLENCODED_VALUE),
                data = mapOf(
                        "client_id" to CLIENT_ID,
                        "client_secret" to CLIENT_SECRET,
                        "grant_type" to "client_credentials"
                ))
                .jsonObject
                .getString("access_token")
    }

}