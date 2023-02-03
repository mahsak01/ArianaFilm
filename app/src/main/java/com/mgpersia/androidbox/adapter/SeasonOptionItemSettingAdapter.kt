package com.mgpersia.androidbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.databinding.LayoutFilterOptionItemBinding

class SeasonOptionItemSettingAdapter(
    private val items: List<FilterOptionBottomSheet>,
    private val itemSelect: List<Int>,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<SeasonOptionItemSettingAdapter.ViewHolder>() {


    private var selectItem: FilterOptionBottomSheet? = null

    inner class ViewHolder(val binding: LayoutFilterOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: FilterOptionBottomSheet) {
            if (itemSelect.contains(item.id)) {
                selectItem = item
                this.binding.layoutFilterOptionItemItemText.isChecked = true
            }
            this.binding.layoutFilterOptionItemItemText.text = item.value

            this.binding.layoutFilterOptionItemItemText.isChecked =
                selectItem != null && item.value == selectItem!!.value

            this.binding.layoutFilterOptionItemItemText.setOnClickListener {
                selectItem = if (this.binding.layoutFilterOptionItemItemText.isChecked) {
                    if (selectItem != null)
                        eventListener.delete(selectItem!!)
                    eventListener.click(item)
                    item
                } else {
                    eventListener.delete(item)
                    null
                }

                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_filter_option_item,
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
        fun click(item: FilterOptionBottomSheet)
        fun delete(item: FilterOptionBottomSheet)
    }
}