package com.android.focusflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.focusflow.data.dao.NoteDao
import com.android.focusflow.data.dao.TaskDao
import com.android.focusflow.data.model.Note
import com.android.focusflow.data.model.Task

@Database(
    entities = [Note::class, Task::class],
    version = 1,
    exportSchema = false
)
abstract class FocusFlowDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
}

