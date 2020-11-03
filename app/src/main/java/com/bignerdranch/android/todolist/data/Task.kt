package com.bignerdranch.android.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey
    val id : UUID = UUID.randomUUID(),
    val title : String,
    val creationDate: Date,
    val dueDate: Date?=null,
    val status : Boolean = false,
    val description : String
)