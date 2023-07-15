package com.example.lemonnotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lemonnotes.data.local.dao.NoteDao
import com.example.lemonnotes.data.local.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}