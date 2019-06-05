package me.ccampo.nexia.api.client

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.jackson.responseObject
import me.ccampo.nexia.api.NexiaConfiguration
import me.ccampo.nexia.api.client.model.HouseEvent
import me.ccampo.nexia.api.client.session.SessionManager
import me.ccampo.nexia.util.X_REQUESTED_WITH
import me.ccampo.nexia.util.cookie
import me.ccampo.nexia.util.jacksonInit
import me.ccampo.nexia.util.result


class NexiaClient(
        private val config: NexiaConfiguration,
        private val sessionManager: SessionManager,
        private val fuel: FuelManager
) {

    init {
        jacksonInit()
    }

    fun getHouseEvents(house: String): List<HouseEvent> {
        val responseObject = fuel.get("${config.baseUrl}/houses/$house/events")
                .cookie(sessionManager.session.type, sessionManager.session.token)
                .header(Headers.X_REQUESTED_WITH to "XMLHttpRequest")
                .header(Headers.ACCEPT to "text/javascript")
                .responseObject<List<HouseEvent>>()

        return responseObject.result().get()
    }
}
