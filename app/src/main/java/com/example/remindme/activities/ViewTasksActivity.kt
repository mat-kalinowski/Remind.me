package com.example.remindme.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.remindme.models.Task
import com.example.remindme.utils.*
import kotlinx.android.synthetic.main.activity_view_tasks.*
import java.util.*
import android.widget.ArrayAdapter
import com.example.remindme.R
import com.example.remindme.adapters.TaskAdapter
import com.example.remindme.models.Header
import com.example.remindme.models.ListElement
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.text.TextWatcher
import com.example.remindme.adapters.RecyclerItemTouch


class ViewTasksActivity : AppCompatActivity() {

    private lateinit var taskList: ArrayList<Task>
    private lateinit var groupList: ArrayList<String>

    private lateinit var taskDate: Date
    private lateinit var taskGroup: String

    private lateinit var adapter: TaskAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)

        groupList = getStorageArray("GROUP_LIST", this@ViewTasksActivity)
        taskList = getStorageArray( "TASK_LIST",this@ViewTasksActivity)

        layoutManager = LinearLayoutManager(this)
        task_recycler.layoutManager = layoutManager

        adapter = TaskAdapter(taskList)
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

    override fun onDestroy(){
        super.onDestroy()

        writeObject(this, "TASK_LIST", taskList)
        writeObject(this, "GROUP_LIST", groupList)
    }

    fun addActivity(v: View){
        val taskName = task_title.text.toString()
        val newTask = Task(taskName, taskDate, taskGroup)

        taskList.add(newTask)
        adapter.filterByDate()
    }

    fun selectGroup(v: View){
        val dialogBuilder = AlertDialog.Builder(this@ViewTasksActivity)
        val groupAdapter = ArrayAdapter<String>(this, R.layout.dialog_item, groupList)

        dialogBuilder.setTitle("Choose task_row group")
        dialogBuilder.setAdapter(groupAdapter, { dialogInterface: DialogInterface, i: Int ->
            taskGroup = groupList[i]
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