package me.ccampo.nexia.api.client

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.LogRequestAsCurlInterceptor
import com.github.kittinunf.fuel.core.interceptors.LogResponseInterceptor
import me.ccampo.nexia.api.NexiaConfiguration
import me.ccampo.nexia.api.client.session.SessionManager
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class NexiaClientTest {

    lateinit var sessionManager: SessionManager

    lateinit var fuel: FuelManager

    lateinit var client: NexiaClient

    @BeforeTest
    fun setUp() {
        val config = NexiaConfiguration("test", "test")
        fuel = FuelManager()
        fuel.addRequestInterceptor(LogRequestAsCurlInterceptor)
        fuel.addResponseInterceptor(LogResponseInterceptor)
        sessionManager = SessionManager(config, fuel)
        client = NexiaClient(config, sessionManager, fuel)
    }

    @Test
    fun getHouseEvents() {
        val houseEvents = client.getHouseEvents("test")
        assertThat(houseEvents).isNotEmpty
    }

    @Test
    fun getThermostats() {
        val thermostats = client.getThermostats("test")
        assertThat(thermostats).isNotEmpty
    }
}
