package com.bignerdranch.android.todolist.data

import androidx.lifecycle.LiveData

class TaskRepository (private val taskDao: TaskDao) {

    val readAllTask : LiveData<List<Task>> = taskDao.readAllTask()

    suspend fun addTask(task : Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
}