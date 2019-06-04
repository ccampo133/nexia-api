package me.ccampo.nexia.api.client.session

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.isSuccessful
import me.ccampo.nexia.api.NexiaConfiguration
import me.ccampo.nexia.util.cookie
import me.ccampo.nexia.util.response
import org.jsoup.Jsoup
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class SessionManager(val config: NexiaConfiguration, val fuel: FuelManager) {

    var session: Session? = null
        private set
        @Synchronized get() {
            if (field == null) {
                field = login()
                return field
            }

            //TODO: I think there's a potential race condition here... investigate -ccampo 2019-06-04
            val ttl = Duration.between(Instant.now(), field!!.expiresAt)

            // Session is about to expire... renew it
            if (ttl.isNegative || ttl <= Duration.of(1, ChronoUnit.MINUTES)) {
                field = login()
                return field
            }

            return field
        }

    private fun login(): Session {
        val (_, loginResponse, loginResult) = fuel.get("${config.baseUrl}/login")
                .allowRedirects(false)
                .responseString()

        val loginDom = Jsoup.parse(loginResult.get())
        val csrfParam = loginDom.select("meta[name=${config.csrfParamMetaTag}]").first().attr("content")
        val csrfToken = loginDom.select("meta[name=${config.csrfTokenMetaTag}]").first().attr("content")

        val params = listOf(
                "login" to config.username,
                "password" to config.password,
                "utf8" to "✓",
                csrfParam to csrfToken
        )

        val loginSession = loginResponse.cookie(config.sessionCookieName)
        val response = fuel.post("${config.baseUrl}/session", params)
                .cookie(config.sessionCookieName, loginSession)
                .allowRedirects(false)
                .response()
                .response()

        check(response.statusCode == 302) {
            "Expected session endpoint to have HTTP status 302; actual value is ${response.statusCode}"
        }

        val token = response.header(Headers.SET_COOKIE)
                .first { it.startsWith(config.sessionCookieName) }
                .split(";")
                .first()
                .substringAfter("${config.sessionCookieName}=")

        return checkSession(token)
    }

    private fun checkSession(token: String): Session {
        val (_, response, result) = fuel.get("${config.baseUrl}/check_session")
                .cookie(config.sessionCookieName, token)
                .allowRedirects(false)
                .responseString()

        if (!response.isSuccessful) {
            throw IllegalStateException("Unsuccessful session check; could be invalid credentials?")
        }

        if (!"expired".equals(result.get(), ignoreCase = true)) {
            return Session(config.sessionCookieName, token, result.get().toInt())
        }

        throw SessionExpiredException("Session is expired")
    }
}

class SessionExpiredException(msg: String) : Exception(msg)
