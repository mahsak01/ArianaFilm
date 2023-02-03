package com.mgpersia.androidbox.adapter

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.MainContainer
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeToSeenCallback(private val context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false // We don't want support moving items up/down
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (MainContainer.seen) {
            RecyclerViewSwipeDecorator.Builder(
                context,
                c, recyclerView, viewHolder, dX, dY,
                actionState.toFloat().toInt(), isCurrentlyActive
            ).addBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                .addActionIcon(R.drawable.ic_baseline_visibility_24)
                .addSwipeLeftLabel("مشاهده شده")
                .create()
                .decorate()
        } else {
            RecyclerViewSwipeDecorator.Builder(
                context,
                c, recyclerView, viewHolder, dX, dY,
                actionState.toFloat().toInt(), isCurrentlyActive
            ).addBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                .addActionIcon(R.drawable.ic_baseline_visibility_off_24)
                .addSwipeLeftLabel("مشاهده نشده")
                .create()
                .decorate()
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}