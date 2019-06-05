package me.ccampo.nexia.api.client.session

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

data class Session(val type: String, val token: String, private val expiry: Int, val issued: Instant = Instant.now()) {

    val expiresAt: Instant = issued.plusSeconds(expiry.toLong())

    val expiresIn: Duration
        get() = Duration.between(Instant.now(), this.expiresAt)

    val isExpiringSoon: Boolean
        get() = isExpired || expiresIn <= Duration.of(1, ChronoUnit.MINUTES)

    val isExpired: Boolean
        get() = expiresIn.seconds <= 0
}
