package com.rodrigoschonardt.orderservice.util

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : JsonSerializer<LocalDateTime> {
    override fun serialize( date : LocalDateTime?, type : Type?, context : JsonSerializationContext? ) : JsonElement {
        return JsonPrimitive( date?.format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ) )
    }
}