package me.ccampo.nexia.api.client.session

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.LogRequestAsCurlInterceptor
import com.github.kittinunf.fuel.core.interceptors.LogResponseInterceptor
import me.ccampo.nexia.api.NexiaConfiguration
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest
import kotlin.test.Test

class SessionManagerTest {

    lateinit var fuel: FuelManager

    lateinit var manager: SessionManager

    @BeforeTest
    fun setUp() {
        fuel = FuelManager()
        fuel.addRequestInterceptor(LogRequestAsCurlInterceptor)
        fuel.addResponseInterceptor(LogResponseInterceptor)
        val config = NexiaConfiguration("", "")
        manager = SessionManager(config, fuel)
    }

    @Test
    fun loginTest() {
        assertThat(manager.session).isNotNull
    }
}
