package com.example.airnotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.airnotes.data.local.dao.NoteDao
import com.example.airnotes.data.local.entities.NoteChecklist
import com.example.airnotes.data.local.entities.NoteEntity

@Database(entities = [NoteEntity::class, NoteChecklist::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}