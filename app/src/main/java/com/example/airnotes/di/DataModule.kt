package com.example.airnotes.di

import android.content.Context
import androidx.room.Room
import com.example.airnotes.data.local.NoteDataBase
import com.example.airnotes.data.local.entities.NoteEntity
import com.example.airnotes.utils.Constants.NOTE_DATABASE
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
    fun providesDatabase(@ApplicationContext context: Context): NoteDataBase =
        Room.databaseBuilder(context, NoteDataBase::class.java, NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesNamesDao(dataBase: NoteDataBase) = dataBase.noteDao()

    @Provides
    fun provideEntity() = NoteEntity()

}