package com.example.airnotes.data.local

import androidx.room.TypeConverter
import com.example.airnotes.data.local.entities.NoteChecklist
import com.google.gson.Gson
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun listToJson(value: List<NoteChecklist>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<NoteChecklist>::class.java).toList()
}