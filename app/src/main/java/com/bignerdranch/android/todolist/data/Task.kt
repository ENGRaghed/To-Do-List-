package com.bignerdranch.android.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String="",
    val creationDate: Date= Date(),
    var dueDate: Date? =null,
    val status: Boolean = false,
    val description: String=""
) : Parcelable