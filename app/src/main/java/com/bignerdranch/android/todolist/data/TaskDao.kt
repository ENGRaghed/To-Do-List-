package com.bignerdranch.android.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task : Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task : Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllTask() : LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    fun sortByDate() : LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY creationDate ASC")
    fun sortByCreationDate() : LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status")
    fun filterCompletedTask() : LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE NOT status")
    fun filterNotCompletedTask() : LiveData<List<Task>>



}