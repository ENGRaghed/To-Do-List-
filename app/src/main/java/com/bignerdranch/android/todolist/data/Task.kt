package com.bignerdranch.android.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "task_table")
data class Task (
    @PrimaryKey
    val id : UUID = UUID.randomUUID(),
    val title : String,
    val creationDate: Date,
    val dueDate: Date?=null,
    val status : Boolean = false,
    val description : String
) : Parcelable