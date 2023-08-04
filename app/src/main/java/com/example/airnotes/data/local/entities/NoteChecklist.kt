package com.example.airnotes.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.airnotes.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.CHECKLIST_TABLE,
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = ["note_id"],
        childColumns = ["note_id"]
    )])
data class NoteChecklist (

    @PrimaryKey(autoGenerate = true)
    var checklistId: Long = 0,

    @ColumnInfo(name = "note_id", index = true)
    var noteId: Long = 0,

    @ColumnInfo(name = "checklist_element")
    var checklistElement: String? = "",

    @ColumnInfo(name = "checked")
    var checked: Boolean = false

) : Serializable {
    override fun toString(): String {
        return "$checklistElement"
    }
}