package com.example.remindme.adapters

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.remindme.models.ListElement
import android.support.v4.content.ContextCompat
import android.content.Context
import android.util.Log
import com.example.remindme.R


class RecyclerItemTouch(adapter: TaskAdapter, context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var adapter: TaskAdapter
    private var context: Context

    init{
        this.adapter = adapter
        this.context = context
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

    override fun onChildDraw( c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isActive)

        val icon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_black_18dp)

        val iconMargin = (viewHolder.itemView.height - icon!!.intrinsicHeight) / 2
        val rightMargin = 20

        icon!!.setBounds(viewHolder.itemView.right - icon.intrinsicWidth - rightMargin,
            viewHolder.itemView.top + iconMargin,
            viewHolder.itemView.right - rightMargin,
            viewHolder.itemView.bottom - iconMargin)
        icon.alpha = 100
        c.clipRect(viewHolder.itemView.right + dX.toInt(), viewHolder.itemView.top, viewHolder.itemView.right, viewHolder.itemView.bottom)
        icon.draw(c)

    }
}