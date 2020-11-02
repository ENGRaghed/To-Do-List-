package com.bignerdranch.android.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val creationDate: Date,
    val dueDate: Date,
    val status : Boolean,
    val description : String
)