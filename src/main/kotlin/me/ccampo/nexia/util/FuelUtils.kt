package me.ccampo.nexia.util

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.kittinunf.result.Result

fun <T : Any> ResponseResultOf<T>.request(): Request {
    return this.first
}

fun <T : Any> ResponseResultOf<T>.response(): Response {
    return this.second
}

fun <T : Any> ResponseResultOf<T>.result(): Result<T, FuelError> {
    return this.third
}

fun Response.cookie(cookieName: String): String {
    return this.header(Headers.SET_COOKIE)
            .first { it.startsWith(cookieName) }
            .substringBefore(";")
            .substringAfter("=")
}

fun Request.cookie(name: String, value: String): Request = this.header(Headers.COOKIE to "$name=$value")
