package com.flemis.score.core.utils


import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

//conversor do room que utiliza typeConverter
class ConvertersRoom {
    @TypeConverter
    fun listToJson(value: List<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun setToJson(value: Set<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun jsonToSet(value: String) = Json.decodeFromString<Set<String>>(value)
}

//padrão para conversao de json
class Converters {
    fun listToJson(value: List<String>?) = Json.encodeToString(value)

    fun jsonToList(value: String) = Json.decodeFromString<List<String>>(value)

    fun setToJson(value: Set<String>?) = Json.encodeToString(value)

    fun jsonToSet(value: String) = Json.decodeFromString<Set<String>>(value)
}