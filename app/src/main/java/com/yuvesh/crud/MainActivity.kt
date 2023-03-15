package com.yuvesh.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yuvesh.adapter.TaskListAdapter
import com.yuvesh.crud.database.AddTask
import com.yuvesh.crud.database.DatabaseHelper
import com.yuvesh.model.TaskListModel

class MainActivity : AppCompatActivity() {

    lateinit var recyclerTask:RecyclerView
    lateinit var btnAdd:Button
    var taskListAdapter:TaskListAdapter?=null
    var dbHandler:DatabaseHelper?=null
    var taskList:List<TaskListModel> =ArrayList<TaskListModel>()
    var linearLayoutManager:LinearLayoutManager ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerTask=findViewById(R.id.rv_list)
        btnAdd=findViewById(R.id.btn_add_items)

        dbHandler= DatabaseHelper(this@MainActivity)

        fetchList()

        btnAdd.setOnClickListener {
            val i = Intent(this@MainActivity, AddTask::class.java)
             startActivity(i)
        }

    }

    private fun fetchList()
    {

         taskList=dbHandler!!.getAllTask()
        taskListAdapter= TaskListAdapter(taskList,this@MainActivity)
        linearLayoutManager= LinearLayoutManager(this@MainActivity)
        recyclerTask.layoutManager=linearLayoutManager
        recyclerTask.adapter=taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }
}