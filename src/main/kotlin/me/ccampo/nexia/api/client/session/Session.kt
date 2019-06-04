package me.ccampo.nexia.api.client.session

import java.time.Instant

data class Session(val type: String, val token: String, val expiry: Int, val issued: Instant = Instant.now()) {
    val expiresAt: Instant = issued.plusMillis(expiry.toLong())
}
