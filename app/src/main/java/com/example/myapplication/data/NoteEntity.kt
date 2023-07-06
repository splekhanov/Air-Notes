package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.Constants.NOTE_TABLE
import java.io.Serializable

@Entity(tableName = NOTE_TABLE)
data class NoteEntity (

    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0,

    @ColumnInfo(name = "note_title")
    var noteTitle: String? = null,

    @ColumnInfo(name = "note_description")
    var noteDescription: String? = null,

):Serializable {
    override fun toString(): String {
        return "$noteDescription"
    }
}