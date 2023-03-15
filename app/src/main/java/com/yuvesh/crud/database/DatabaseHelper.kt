package com.yuvesh.crud.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yuvesh.model.TaskListModel

class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION) {

    companion object {
        private val DB_NAME = "task"
        private val DB_VERSION = 1
        private val TABLE_NAME = "tasktable"
        private val TASK_ID = "id"
        private val TASK_NAME = "taskname"
        private val TASK_DETAILS = "taskdetails"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + TASK_ID + " INTEGER PRIMARY KEY, " +
                TASK_NAME + "TEXT" + TASK_DETAILS + "TEXT" + ")")


        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllTask(): List<TaskListModel> {
        val taskList = ArrayList<TaskListModel>()
        val db: SQLiteDatabase = writableDatabase
        val selectquery = "SELECT *FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(selectquery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    val tasks = TaskListModel()
                    tasks.id =
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(TASK_ID)))
                    tasks.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
                    tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                    taskList.add(tasks)
                } while (cursor.moveToNext())


            }
        }

        cursor?.close()
        return taskList
    }

    fun addData(tasks: TaskListModel): Long {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()

        values.put(TASK_NAME, tasks.name)
        values.put(TASK_DETAILS, tasks.details)
        val success = db.insert(TABLE_NAME, null, values)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getData(_id: Int): TaskListModel {
        val tasks = TaskListModel()
        val db: SQLiteDatabase = writableDatabase
        val selectQuery = "SELECT  * FROM + $TABLE_NAME +WHERE+ $TASK_ID= $_id"
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()

        tasks.id = Integer.parseInt(cursor!!.getString(cursor.getColumnIndex(TASK_ID)))
        tasks.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
        tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
        cursor.close()
        return tasks

    }
    fun deleteData(_id:Int):Boolean
    {
        val db = this.writableDatabase
        val values = ContentValues()


        values.put(TASK_ID,_id)
        val success:Long = db.update(TABLE_NAME,values, TASK_ID+"=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success")!=-1
    }
    fun updateData(tasks: TaskListModel):Boolean
    {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()

        values.put(TASK_NAME, tasks.name)
        values.put(TASK_DETAILS,tasks.details)
        val success:Long = db.update(TABLE_NAME,values, TASK_ID+"=?", arrayOf(tasks.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success")!=-1
    }
}