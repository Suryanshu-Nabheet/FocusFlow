package com.android.focusflow.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FocusFlowDatabase {
        return Room.databaseBuilder(
            context,
            FocusFlowDatabase::class.java,
            "focusflow_database"
        )
        .fallbackToDestructiveMigration() // Allow database recreation on schema changes
        .build()
    }

    @Provides
    fun provideNoteDao(database: FocusFlowDatabase) = database.noteDao()

    @Provides
    fun provideTaskDao(database: FocusFlowDatabase) = database.taskDao()
}

