package com.example.airnotes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.airnotes.utils.Constants
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = Constants.NOTE_TABLE)
data class NoteEntity(

    @ColumnInfo(name = "note_id", index = true)
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0,

    @ColumnInfo(name = "note_title")
    var title: String? = "",

    @ColumnInfo(name = "note_description")
    var description: String? = "",

    @ColumnInfo(name = "note_type")
    var noteType: NoteType = NoteType.TEXT,

    @ColumnInfo(name = "date")
    var date: LocalDateTime = LocalDateTime.now(),

    @Ignore
    var noteChecklist: List<NoteChecklist> = emptyList(),

    ) : Serializable {

    override fun toString(): String {
        return "$title"
    }
}