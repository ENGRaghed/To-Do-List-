package com.bignerdranch.android.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(task : Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllTask() : LiveData<List<Task>>
}