package com.github.diegocolombo.ifoodbackchallenge.gateway

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.Exception

@Component
class SpotifyGateway {

    private val URL = "https://api.spotify.com/v1/browse/categories"
    private val CLIENT_ID = "ac6240ec42734f31b3c9940964ce6578"
    private val CLIENT_SECRET = "4aea202fd2e94039bafb7d0695f4310d"

    fun getPlaylistByGenre(genre: String): List<String> {
        val headers = HttpHeaders()
                .also { it.set("Authorization", "Bearer ${login()}") }
                .let { HttpEntity("parameters", it) }
        val body = RestTemplate()
                .exchange("$URL/$genre/playlists", HttpMethod.GET, headers, Map::class.java)
                .body!!["playlists"] as Map<String, Any>
        val tracks = body
                .let { it["items"] as List<Map<String, Any>> }
                .first()
                .let { it["tracks"] as Map<String, Any> }
                .let { it["href"] as String }
                .let { RestTemplate().exchange(it, HttpMethod.GET, headers, Map::class.java).body }
                .let { it!!["items"] as List<Map<String, Any>> }
        return tracks
                .map { it["track"] as Map<String, Any> }
                .map { it["name"] as String }
    }

    private fun login(): String {
        val entity = HttpHeaders().also { it.contentType = MediaType.APPLICATION_FORM_URLENCODED }
                .let { HttpEntity("client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&grant_type=client_credentials", it) }
        return RestTemplate().exchange(
                "https://accounts.spotify.com/api/token",
                HttpMethod.POST,
                entity,
                Map::class.java)
                .body
                .let { it!!["access_token"] as String }
    }

}