package com.android.focusflow.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val dueDate: Long? = null, // Timestamp in milliseconds
    val reminderTime: Long? = null, // Timestamp in milliseconds
    val priority: Int = 0, // 0 = low, 1 = medium, 2 = high
    val createdAt: Long = System.currentTimeMillis()
)

