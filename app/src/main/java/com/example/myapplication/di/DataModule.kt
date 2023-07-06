package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.NoteEntity
import com.example.myapplication.data.NotesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): NotesDataBase =
        Room.databaseBuilder(context, NotesDataBase::class.java, "notes.db").allowMainThreadQueries().build()

    @Provides
    fun providesNamesDao(dataBase: NotesDataBase) = dataBase.noteDao()

    @Provides
    fun provideEntity() = NoteEntity()
}