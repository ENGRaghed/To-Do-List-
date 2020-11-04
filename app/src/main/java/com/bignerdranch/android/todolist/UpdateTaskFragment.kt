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
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.todolist.data.Task
import com.bignerdranch.android.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_update_task.*
import kotlinx.android.synthetic.main.fragment_update_task.view.*


class UpdateTaskFragment : Fragment() {

    private val args by navArgs<UpdateTaskFragmentArgs>()

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_update_task, container, false)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        view.update_title_et.setText(args.task.title)
        view.update_desc_et.setText(args.task.description)
        view.update_status.isChecked=args.task.status

        view.update_button.setOnClickListener {
            updateTask()
        }

        return view
    }

    private fun updateTask(){
        val title = update_title_et.text.toString()
        val desc = update_desc_et.text.toString()
        val status = update_status.isChecked

        if (inputCheck(title,desc)){
            val updatedTask = Task(
                id = args.task.id, title = title, status = status,
                description = desc, creationDate = args.task.creationDate,
                dueDate = args.task.dueDate)
            taskViewModel.updateTask(updatedTask)

            Toast.makeText(context, "update seccecful", Toast.LENGTH_SHORT).show()

            //navigate back
            findNavController().navigate(R.id.action_updateTaskFragment_to_taskListFragment)
        }else{
            Toast.makeText(context, "complete task info", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(title : String , desc : String) : Boolean {
        return !(TextUtils.isEmpty(title)|| TextUtils.isEmpty(desc))
    }

}