package com.github.diegocolombo.ifoodbackchallenge.controllers

import com.github.diegocolombo.ifoodbackchallenge.service.PlaylistService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
class PlaylistController(val playlistService: PlaylistService) {

    @GetMapping("playlist/city/{city}")
    fun getPlaylistByCity(@PathVariable("city") city: String): ResponseEntity<List<String>> {
        return ResponseEntity.ok(playlistService.getPlaylistByCity(city))
    }

    @GetMapping("playlist/lat/{lat}/lon/{lon}")
    fun getPlaylistByCoordinate(@PathVariable("lat") lat: Double,
                                @PathVariable("lon") lon: Double): ResponseEntity<List<String>> {
        return ResponseEntity.ok(listOf("Garupa 2", lat.toString(), lon.toString()))
    }

}