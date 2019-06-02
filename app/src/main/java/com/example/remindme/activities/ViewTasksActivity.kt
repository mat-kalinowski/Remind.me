package com.example.remindme.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.arch.persistence.room.Room
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_view_tasks.*
import java.util.*
import android.widget.ArrayAdapter
import com.example.remindme.R
import com.example.remindme.adapters.TaskAdapter
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.remindme.adapters.RecyclerItemTouch
import com.example.remindme.app_database.AppDatabase
import com.example.remindme.app_database.Group
import com.example.remindme.app_database.Task
import java.lang.Exception

class ViewTasksActivity : AppCompatActivity() {

    private var groupList: ArrayList<Group> = ArrayList()

    private lateinit var taskDate: Date
    private lateinit var taskGroup: String

    private lateinit var dbHandler: AppDatabase
    private lateinit var adapter: TaskAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)

        AsyncTask.execute{
            try {
                dbHandler = Room.databaseBuilder(
                    this,
                    AppDatabase::class.java,
                    "remindme.db"
                ).build()
            } catch (e: Exception) {
                Log.e("DB ERROR: ", " cannot connect to database")
                Log.e("DB ERROR: ", e.message)
            }

            groupList = dbHandler.groupDao().getAll() as ArrayList<Group>
        }

        layoutManager = LinearLayoutManager(this)
        task_recycler.layoutManager = layoutManager

        adapter = TaskAdapter(this)
        task_recycler.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouch(adapter, this@ViewTasksActivity))
        itemTouchHelper.attachToRecyclerView(task_recycler)

        adapter.filterByDate()

        search_field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filterByText(p0.toString())
            }
        })
    }

    fun addActivity(v: View){
        val taskName = task_title.text.toString()
        val newTask = Task(taskName, taskDate, taskGroup)

        adapter.addItem(newTask)
    }

    fun selectGroup(v: View){
        val dialogBuilder = AlertDialog.Builder(this@ViewTasksActivity)
        val groupAdapter = ArrayAdapter<Group>(this, R.layout.dialog_item, groupList)

        dialogBuilder.setTitle("Choose task_row group")
        dialogBuilder.setAdapter(groupAdapter, { dialogInterface: DialogInterface, i: Int ->
            taskGroup = groupList[i].name
            group_title.setText(taskGroup) })

        val alert = dialogBuilder.create()
        alert.show()
    }

    fun selectDate(v: View){
        val c = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            displayDate.text = "" + day + "/" + (month+1) + "/" + year
            taskDate = Date(year,month,day)

        }, c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    fun changeFilter(v: View){
        if(change_display.isChecked){
            change_display.setText("Group")
            adapter.filterByGroup()
        }
        else{
            change_display.setText("Date")
            adapter.filterByDate()
        }
    }

    fun backToMenu(v: View){
        val mainMenu = Intent(this@ViewTasksActivity, MainActivity::class.java)
        startActivity(mainMenu)
        finish()
    }

    fun showDisplayBar(v: View){
        if(bar_holder.displayedChild == 2 && bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 2
        }
    }

    fun showSearchBar(v: View){
        if(bar_holder.displayedChild == 1 && bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 1
        }
    }
    fun showTaskBar(v: View){
        if(bar_holder.displayedChild == 0 && bar_holder.visibility == View.VISIBLE){
            bar_holder.visibility = View.GONE
        }
        else{
            bar_holder.visibility = View.VISIBLE
            bar_holder.displayedChild = 0
        }
    }
}