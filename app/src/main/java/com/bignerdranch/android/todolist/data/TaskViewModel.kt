package com.bignerdranch.android.todolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val readAllTask : LiveData<List<Task>>
    private val repository : TaskRepository
    val storByDate : LiveData<List<Task>>
    val sortByCreationDate : LiveData<List<Task>>
    val filterCompletedTask : LiveData<List<Task>>
    val filterNotCompletedTask :LiveData<List<Task>>


    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllTask = repository.readAllTask
        storByDate = repository.sortByDate
        sortByCreationDate = repository.sortByCreationDate
        filterCompletedTask = repository.filterCompletedTask
        filterNotCompletedTask = repository.filterNotCompletedTask


    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(task)
        }
    }

}