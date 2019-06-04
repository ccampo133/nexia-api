package me.ccampo.nexia.api

data class NexiaConfiguration(
        val username: String,
        val password: String,
        val baseUrl: String = "https://www.mynexia.com",
        val sessionCookieName: String = "_SchlagePortal_session",
        val csrfParamMetaTag: String = "csrf-param",
        val csrfTokenMetaTag: String = "csrf-token"
)
