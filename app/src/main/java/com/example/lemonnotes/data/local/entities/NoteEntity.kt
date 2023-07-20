package com.example.lemonnotes.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lemonnotes.utils.Constants.NOTE_TABLE
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "note_title")
    var noteTitle: String? = "",

    @ColumnInfo(name = "note_description")
    var noteDescription: String? = "",

    @ColumnInfo(name = "date")
    val date: LocalDateTime = LocalDateTime.now()

    ) : Serializable {
    override fun toString(): String {
        return "$noteDescription"
    }
}