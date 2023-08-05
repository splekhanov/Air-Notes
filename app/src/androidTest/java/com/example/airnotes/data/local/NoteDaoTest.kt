package com.example.airnotes.data.local

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.airnotes.data.local.dao.NoteDao
import com.example.airnotes.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class NoteDaoTest {

    private lateinit var database: NoteDataBase
    private lateinit var notesDao: NoteDao

    @BeforeEach
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NoteDataBase::class.java).build()
        notesDao = database.noteDao()
    }

    @Test
    fun insertNote()  = runTest {
        val note = NoteEntity(1,"Title")
        notesDao.insertNote(note)
        val notesList = notesDao.getAllNotes().asFlow().first()
        assertTrue(notesList.contains(note))
    }

    @AfterEach
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }
}