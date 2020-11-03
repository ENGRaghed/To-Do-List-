package com.bignerdranch.android.todolist.data

import android.content.Context
import androidx.room.*

@Database(entities = [Task::class], version = 1)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDao() : TaskDao

    companion object{
        @Volatile
        private var INSTANCE : TaskDatabase? = null

        fun getDatabase(context: Context):TaskDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}