package com.bignerdranch.android.todolist

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task_details.view.*


class TaskDetailsFragment : Fragment() {

    private val args by navArgs<TaskDetailsFragmentArgs>()

    private lateinit var taskViewModel: TaskViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_task_details, container, false)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        view.title_tv.text = args.currentTask.title
        view.desc_tv.text = args.currentTask.description

//        view.status_cb.isChecked = args.currentTask.status



        view.creation_date_tv.text = DateFormat.format(" EEEE ,MMM dd, yyyy.", args.currentTask.creationDate)
        if (args.currentTask.dueDate!=null){
            view.due_date_tv.text = DateFormat.format(" EEEE ,MMM dd, yyyy.", args.currentTask.dueDate)
        }else{
            view.due_date_tv.text ="not selected"
        }




        return view
    }


}