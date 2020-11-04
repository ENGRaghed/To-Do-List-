package com.bignerdranch.android.todolist

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.todolist.data.Task
import com.bignerdranch.android.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*
import java.util.*

private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0
class AddTaskFragment : Fragment(),DatePickerFragment.Callbacks {

    private lateinit var taskViewModel : TaskViewModel

    private var dueDate : Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add_task, container, false)

        val task = Task()
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        view.add_button.setOnClickListener{
            insertTaskToDatabase()
        }


        view.task_due_date.setOnClickListener { DatePickerFragment.newInstance(Date()).apply {
            setTargetFragment(this@AddTaskFragment, REQUEST_DATE)
            show(this@AddTaskFragment.requireFragmentManager(), DIALOG_DATE)
        } }

        view.task_due_date.setText("add due date")


        return view
    }

    private fun insertTaskToDatabase() {

        val title = task_title_et.text.toString()
        val desc = task_desc_et.text.toString()
        if (inputCheck(title,desc)) {

            //create task object
            val task: Task = Task(title = title, creationDate = Date(), description = desc,dueDate = dueDate)

            //add task
            taskViewModel.addTask(task)
            Toast.makeText(context, "add seccecful", Toast.LENGTH_SHORT).show()

            //navigate back
            findNavController().navigate(R.id.action_addTaskFragment_to_taskListFragment)
        }else{
            Toast.makeText(context, "complete task info", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(title : String , desc : String) : Boolean {
        return !(TextUtils.isEmpty(title)||TextUtils.isEmpty(desc))
    }

    override fun onDateSelected(date: Date) {
        this.dueDate=date
        task_due_date.setText(date.toString())


    }



}