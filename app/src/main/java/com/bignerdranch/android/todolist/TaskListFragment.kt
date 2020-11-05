package com.bignerdranch.android.todolist

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.todolist.data.Task
import com.bignerdranch.android.todolist.data.TaskViewModel
import com.bignerdranch.android.todolist.swipe.MyButton
import com.bignerdranch.android.todolist.swipe.MyButtonClickListener
import com.bignerdranch.android.todolist.swipe.MySwipeHelper
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import kotlinx.android.synthetic.main.task_item_rv.view.*
import java.text.SimpleDateFormat
import java.util.*


class TaskListFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    lateinit var  adapter : TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View= inflater.inflate(R.layout.fragment_task_list, container, false)

        //recyclerView
        adapter = TaskListAdapter()
        val recyclerView = view.task_recyclerView
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(context)

       // val item = object : SwipeToDelete(this,0,ItemTouchHelper.LEFT)


        //viewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.readAllTask.observe(viewLifecycleOwner, Observer {tasks ->
            adapter.setTasks(tasks)
        })

        //add swipe

        val swipe = object : MySwipeHelper(requireContext(),recyclerView,200)
        {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                //add button
                buffer.add(
                    MyButton(requireContext(),
                        "delete",
                        30,
                        R.drawable.ic_delete_24,
                        Color.parseColor("#FF3c30"),
                        object :
                            MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                deleteTask(adapter.taskList[pos])
                            }
                        })
                )

                //edit button
                buffer.add(
                    MyButton(requireContext(),
                        "edit",
                        30,
                        R.drawable.ic_edit_white_24,
                        Color.parseColor("#FF9502"),
                        object :
                            MyButtonClickListener {
                            override fun onClick(pos: Int) {

                                val action =
                                    TaskListFragmentDirections.actionTaskListFragmentToUpdateTaskFragment(
                                        adapter.taskList[pos]
                                    )
                                findNavController().navigate(action)
                            }
                        })
                )

            }

        }


        return view
    }

   inner class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

       lateinit var currentTask : Task
       var taskList = emptyList<Task>()
        inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view){
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.task_item_rv,parent,false)
            return TaskViewHolder(view)
        }

        override fun getItemCount(): Int {
            return taskList.size
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            currentTask= taskList[position]
            holder.itemView.title_item.text=currentTask.title
            if (currentTask.dueDate!=null){
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val d1 = simpleDateFormat.format(Date())
                val d2 = simpleDateFormat.format(currentTask.dueDate)
                if(d1.compareTo(d2) > 0){
                    holder.itemView.date_item.setTextColor(Color.GRAY)
                } else if(d1.compareTo(d2) < 0){
                    holder.itemView.date_item.setTextColor(Color.BLACK)

                } else if(d1.compareTo(d2) == 0){
                    holder.itemView.date_item.setTextColor(Color.RED)

                }
                holder.itemView.date_item.text=currentTask.dueDate.toString()
            }else{
                holder.itemView.date_item.text=""
            }
            if (currentTask.status){
                holder.itemView.complete_item.visibility= View.VISIBLE
            }else{
                holder.itemView.complete_item.visibility= View.GONE

            }
            holder.itemView.task_item_layout.setOnClickListener {
                val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(taskList[position])
                holder.itemView.findNavController().navigate(action)
            }


        }
       fun setTasks(tasks : List<Task>){
           this.taskList=tasks
           notifyDataSetChanged()
       }
    }

    private fun deleteTask(task: Task){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes"){_, _ ->
            taskViewModel.deleteTask(task)
            Toast.makeText(requireContext(),"deleted ${task.title}",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("no"){_, _ ->

        }
        builder.setTitle("delete ${task.title}")
        builder.setMessage("are you want to delete ${task.title} ?")
        builder.create().show()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_task_list,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_task -> findNavController().navigate(R.id.action_taskListFragment_to_addTaskFragment)
            R.id.menu_item_store -> taskViewModel.storByDate.observe(this, Observer { adapter.setTasks(it) })
            R.id.sort_creation_date -> taskViewModel.sortByCreationDate.observe(this, Observer { adapter.setTasks(it) })
            R.id.complete -> taskViewModel.filterCompletedTask.observe(this, Observer { adapter.setTasks(it) })
            R.id.not_complete -> taskViewModel.filterNotCompletedTask.observe(this, Observer { adapter.setTasks(it) })
            R.id.all_task -> taskViewModel.readAllTask.observe(this, Observer { adapter.setTasks(it) })
        }
            return super.onOptionsItemSelected(item)
    }



}