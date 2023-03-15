package com.yuvesh.crud.database

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.yuvesh.crud.MainActivity
import com.yuvesh.crud.R
import com.yuvesh.model.TaskListModel

class AddTask: AppCompatActivity() {

    lateinit var btnSave: Button
    lateinit var btnDelete:Button
    lateinit var etName: EditText
    lateinit var etDetails:EditText
    var databaseHelper:DatabaseHelper?=null
    var isEditMode:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        btnSave=findViewById(R.id.btn_save)
        btnDelete=findViewById(R.id.btn_delete)
        etName=findViewById(R.id.et_name)
        etDetails=findViewById(R.id.et_details)

        databaseHelper= DatabaseHelper(this@AddTask)

        if(intent!=null && intent.getStringExtra("Mode")=="E") {
            isEditMode = true
            btnSave.text = "Update data"
            btnDelete.visibility = View.VISIBLE

            val tasks: TaskListModel =databaseHelper!!.getData(intent.getIntExtra("Id",0))
            etName.setText(tasks.name)
            etDetails.setText(tasks.details)
        }
        else
        {
            isEditMode = false
            btnSave.text = "Save data"
            btnDelete.visibility = View.GONE
        }

        btnSave.setOnClickListener {
            var success:Boolean=false
            var tasks:TaskListModel = TaskListModel()

            if(isEditMode)
            {
                tasks.id=intent.getIntExtra("Id",0)
                tasks.name=etName.text.toString()
                tasks.details=etDetails.text.toString()
                success=databaseHelper?.updateData(tasks) as Boolean
            }
            else
            {
                tasks.name=etName.text.toString()
                tasks.details=etDetails.text.toString()

                success=databaseHelper?.addData(tasks) as Boolean
            }

            if(success)
            {
                val i= Intent(this@AddTask, MainActivity::class.java)
                startActivity(i)
                finish()
            }
            else
            {
                Toast.makeText(this@AddTask,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {

            val dialog= AlertDialog.Builder(this).setTitle("Info").setMessage("Click yes if you want to delete")
                .setPositiveButton("Yes") { dialog, _ ->
                    val success =
                        databaseHelper?.deleteData(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

            dialog.show()
        }
    }
}