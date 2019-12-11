package com.hongbeomi.findtaek.util

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hongbeomi.findtaek.view.adapter.MainAdapter

/**
 * @author hongbeomi
 */

class RecyclerItemTouchHelper(
    dragDirs: Int,
    swipeDirs: Int,
    private val listener: RecyclerItemTouchHelperListener
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = true


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let { v ->
            (v as MainAdapter.MainViewHolder).viewForeground.let {
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(it)
            }
        }
    }

    override fun onChildDrawOver(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) =
        (viewHolder as MainAdapter.MainViewHolder).viewForeground.let {
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(
                c, recyclerView, it, dX, dY,
                actionState, isCurrentlyActive
            )
        }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) =
        (viewHolder as MainAdapter.MainViewHolder).viewForeground.let {
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView(it)
        }


    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) {
        val foregroundView = (viewHolder as MainAdapter.MainViewHolder).viewForeground

        ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(
            c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive
        )
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}