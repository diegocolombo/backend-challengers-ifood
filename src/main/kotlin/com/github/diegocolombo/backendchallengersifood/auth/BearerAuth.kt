package com.github.diegocolombo.backendchallengersifood.auth

import khttp.structures.authorization.Authorization

data class BearerAuth(private val token: String): Authorization {
    override val header: Pair<String, String>
        get() = "Authorization" to "Bearer $token"
}