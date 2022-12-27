package com.example.todomateclone.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")

    @FromJson()
    fun fromJson(raw: String): LocalDateTime {
        return LocalDateTime.parse(
            raw,
            formatter,
        )
    }

    @ToJson()
    fun toJson(dateTime: LocalDateTime): String {
        return DateTimeFormatter.ISO_INSTANT.format(dateTime)
    }
}