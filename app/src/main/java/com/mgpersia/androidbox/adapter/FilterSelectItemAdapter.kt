package com.mgpersia.androidbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.databinding.LayoutFilterSelectItemBinding

class FilterSelectItemAdapter(
    private val items: List<FilterOptionBottomSheet>,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<FilterSelectItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutFilterSelectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(filterOptionBottomSheet: FilterOptionBottomSheet) {
            this.binding.layoutFilterItemSelectNameTv.text = filterOptionBottomSheet.value
            this.binding.layoutFilterItemSelectRemoveBtn.setOnClickListener {
                eventListener.delete(filterOptionBottomSheet)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_filter_select_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int = items.size

    interface EventListener {
        fun delete(filterOptionBottomSheet: FilterOptionBottomSheet)
    }
}