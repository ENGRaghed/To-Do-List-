package com.bignerdranch.android.todolist.data

import androidx.lifecycle.LiveData

class TaskRepository (private val taskDao: TaskDao) {

    val readAllTask : LiveData<List<Task>> = taskDao.readAllTask()
    val sortByDate : LiveData<List<Task>> = taskDao.sortByDate()
    val filterCompletedTask : LiveData<List<Task>> = taskDao.filterCompletedTask()
    val filterNotCompletedTask : LiveData<List<Task>> = taskDao.filterNotCompletedTask()
    val sortByCreationDate : LiveData<List<Task>> = taskDao.sortByCreationDate()

    suspend fun addTask(task : Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
}