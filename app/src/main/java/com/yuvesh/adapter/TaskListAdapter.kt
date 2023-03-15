package com.yuvesh.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuvesh.crud.R
import com.yuvesh.crud.database.AddTask
import com.yuvesh.model.TaskListModel

class TaskListAdapter(taskList:List<TaskListModel>,var context: Context):RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {


    var taskList:List<TaskListModel> =ArrayList()
    init {
        this.taskList= taskList
    }
class TaskViewHolder(view:View):RecyclerView.ViewHolder(view)
{

     var name:TextView= view.findViewById(R.id.txt_name)
    var details:TextView= view.findViewById(R.id.txt_details)
    var btnEdit:Button= view.findViewById(R.id.btn_edit)
}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

         val view=LayoutInflater.from(context).inflate(R.layout.recycler_task_list,parent,false)
         return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

          val tasks=taskList[position]
          holder.name.text=tasks.name
        holder.details.text=tasks.details

        holder.btnEdit.setOnClickListener {
            val i=Intent(context, AddTask::class.java)
            i.putExtra("MODE","E")
            i.putExtra("ID",tasks.id)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
       return taskList.size
    }
}