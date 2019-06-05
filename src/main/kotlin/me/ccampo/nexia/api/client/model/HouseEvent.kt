package me.ccampo.nexia.api.client.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class HouseEvent(
        val id: Long,
        val description: String,
        val accountId: String?, //TODO: confirm this type -ccampo 2019-06-05
        val occurred_at: Instant,
        val houseId: Long,
        val systemGuid: UUID,
        val noise: Boolean,
        val sourceType: String,
        val eventType: String, //TODO: this should probably be an enum -ccampo 2019-06-05
        val sourceName: String,
        val actor: String,
        @JsonFormat(pattern = "KK:mm a")
        val time: LocalTime,
        @JsonFormat(pattern = "MM/dd/yyyy")
        val date: LocalDate
)
