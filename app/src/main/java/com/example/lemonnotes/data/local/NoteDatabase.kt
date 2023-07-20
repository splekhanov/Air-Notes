package com.example.lemonnotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lemonnotes.data.local.dao.NoteDao
import com.example.lemonnotes.data.local.entities.NoteEntity
import com.example.lemonnotes.utils.Converters

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}