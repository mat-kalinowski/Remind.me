package com.example.remindme.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.remindme.models.ListElement

class RecyclerItemTouch(adapter: TaskAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var adapter: TaskAdapter

    init{
        this.adapter = adapter
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        var position = p0.adapterPosition

        if(adapter.getItemViewType(position) == ListElement.TASK){
            adapter.deleteItem(position)
        }
        else if(adapter.getItemViewType(position) == ListElement.HEADER){
            adapter.deleteTaskDown(position)
        }
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        return false
    }
}