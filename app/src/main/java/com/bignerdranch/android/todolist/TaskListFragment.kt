package com.bignerdranch.android.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.todolist.data.Task
import com.bignerdranch.android.todolist.data.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import kotlinx.android.synthetic.main.task_item_rv.view.*


class TaskListFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View= inflater.inflate(R.layout.fragment_task_list, container, false)

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
        }


        //recyclerView
        val adapter = TaskListAdapter()
        val recyclerView = view.task_recyclerView
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(context)

        //viewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.readAllTask.observe(viewLifecycleOwner, Observer {tasks ->
            adapter.setTasks(tasks)
        })



        return view
    }

   private inner class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

        private var taskList = emptyList<Task>()
       private inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.task_item_rv,parent,false)
            return TaskViewHolder(view)
        }

        override fun getItemCount(): Int {
            return taskList.size
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val currentTask= taskList[position]
            holder.itemView.title_item.text=currentTask.title
            holder.itemView.date_item.text=currentTask.creationDate.toString()
            if (currentTask.status){
                holder.itemView.complete_item.visibility= View.VISIBLE
            }
            holder.itemView.task_item_layout.setOnClickListener {
                val action = TaskListFragmentDirections.actionTaskListFragmentToUpdateTaskFragment(currentTask)
                holder.itemView.findNavController().navigate(action)
            }
        }
       fun setTasks(tasks : List<Task>){
           this.taskList=tasks
           notifyDataSetChanged()
       }
    }


}