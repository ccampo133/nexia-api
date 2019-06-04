package me.ccampo.nexia.api.client

import com.github.kittinunf.fuel.core.FuelManager
import me.ccampo.nexia.api.NexiaConfiguration
import me.ccampo.nexia.api.client.session.SessionManager


class NexiaClient(val config: NexiaConfiguration, val sessionManager: SessionManager, val fuel: FuelManager) {
    init {
        throw NotImplementedError()
    }
}
