package com.bignerdranch.android.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task : Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllTask() : LiveData<List<Task>>
}