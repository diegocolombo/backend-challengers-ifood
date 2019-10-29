package com.github.diegocolombo.backendchallengersifood.controllers

import com.github.diegocolombo.backendchallengersifood.service.PlaylistService
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
        return ResponseEntity.ok(playlistService.getPlaylistByCoordinates(lat, lon))
    }

}