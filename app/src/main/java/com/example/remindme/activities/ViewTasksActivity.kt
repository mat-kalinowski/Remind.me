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
import com.example.remindme.adapters.RecyclerItemTouch


class ViewTasksActivity : AppCompatActivity() {

    private lateinit var taskList: ArrayList<Task>
    private lateinit var groupList: ArrayList<String>
    private var adapterList: ArrayList<ListElement> = ArrayList()

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

        adapter = TaskAdapter(adapterList, taskList)
        task_recycler.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouch(adapter))
        itemTouchHelper.attachToRecyclerView(task_recycler)

        sortByDate()

    }

    override fun onDestroy(){
        super.onDestroy()

        writeObject(this, "TASK_LIST", taskList)
        writeObject(this, "GROUP_LIST", groupList)
    }

    fun sortByDate(){
        Collections.sort(taskList){ o1, o2 -> o1.getRawDate().compareTo(o2.getRawDate()) }
        adapterList.clear()

        if(taskList.size >0){
            adapterList.add(Header(taskList[0].getDate()))
            adapterList.add(taskList[0])
        }

        for(i in 1 .. (taskList.size-1)){
            if(taskList[i-1].getRawDate() != taskList[i].getRawDate()){
                adapterList.add(Header(taskList[i].getDate()))
            }
            adapterList.add(taskList[i])
        }

        adapter.notifyDataSetChanged()
    }

    fun sortByGroup(){
        Collections.sort(taskList){ o1, o2 -> o1.getGroup().compareTo(o2.getGroup()) }
        adapterList.clear()

        if(taskList.size > 0){
            adapterList.add(Header(taskList[0].getGroup()))
            adapterList.add(taskList[0])
        }

        for(i in 1 .. (taskList.size-1)){
            if(taskList[i-1].getGroup() != taskList[i].getGroup()){
                adapterList.add(Header(taskList[i].getGroup()))
            }
            adapterList.add(taskList[i])
        }

        adapter.notifyDataSetChanged()
    }

    fun addActivity(v: View){
        val taskName = task_title.text.toString()
        val newTask = Task(taskName, taskDate, taskGroup)

        taskList.add(newTask)
        sortByDate()
    }

    fun selectGroup(v: View){
        val dialogBuilder = AlertDialog.Builder(this@ViewTasksActivity)
        val groupAdapter = ArrayAdapter<String>(this, R.layout.dialog_item, groupList)

        dialogBuilder.setTitle("Choose task_row group")
        dialogBuilder.setAdapter(groupAdapter, { dialogInterface: DialogInterface, i: Int ->
            taskGroup = groupList[i]
            group_title.setText(taskGroup)
        })

        val alert = dialogBuilder.create()
        alert.show()
    }

    fun selectDate(v: View){
        val c = Calendar.getInstance()

        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            displayDate.text = "" + day + "/" + month + "/" + year
            taskDate = Date(year,month,day)

        }, c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    fun changeFilter(v: View){
        if(change_display.isChecked){
            change_display.setText("Group")
            sortByGroup()
        }
        else{
            change_display.setText("Date")
            sortByDate()
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