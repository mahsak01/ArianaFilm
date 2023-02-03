package com.mgpersia.androidbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.data.model.Resolution
import com.mgpersia.androidbox.databinding.LayoutFilterOptionItemBinding
import com.mgpersia.androidbox.databinding.LayoutResolutionOptionItemBinding

class ResolutionOptionItemSettingAdapter(
    private val items: List<Resolution>,
    private val itemSelect: Int,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<ResolutionOptionItemSettingAdapter.ViewHolder>() {


    private var selectItem: Resolution? = null

    inner class ViewHolder(val binding: LayoutResolutionOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: Resolution) {
            if (itemSelect == items.indexOf(item)) {
                selectItem = item
                this.binding.layoutResolutionOptionItemItemText.isChecked = true
            }
            this.binding.layoutResolutionOptionItemItemText.text = "کیغیت" + item.resolution
            this.binding.layoutResolutionOptionItemItemText.isChecked =
                selectItem != null && item.resolution == selectItem!!.resolution
            this.binding.layoutResolutionOptionItemItemText.setOnClickListener {
                selectItem = if (this.binding.layoutResolutionOptionItemItemText.isChecked) {
                    if (selectItem != null)
                        eventListener.delete(selectItem!!)
                    eventListener.click(item)
                    item
                } else {
                    eventListener.delete(item)
                    eventListener.click(item)
                    item
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_resolution_option_item,
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
        fun click(item: Resolution)
        fun delete(item: Resolution)
    }
}