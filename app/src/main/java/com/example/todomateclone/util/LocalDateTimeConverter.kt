package com.example.todomateclone.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @FromJson()
    fun fromJson(raw: String): LocalDate {
        return LocalDate.parse(
            raw,
            formatter,
        )
    }

    @ToJson()
    fun toJson(dateTime: LocalDate): String {
        return DateTimeFormatter.ISO_INSTANT.format(dateTime)
    }
}