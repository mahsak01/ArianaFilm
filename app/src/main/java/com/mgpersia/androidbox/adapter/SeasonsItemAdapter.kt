package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.Episode
import com.mgpersia.androidbox.databinding.LayoutSeasonItemBinding

class SeasonsItemAdapter(
    private val context: Context,
    private val episodeItems: List<Episode>,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<SeasonsItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutSeasonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: Episode) {
            if (item.name_s!=null)
            this.binding.layoutSeasonItemFilmNameTv.text=item.name_s
            this.binding.layoutSeasonItemTimeMinTv.text=item.duration.toString()+" دقیقه"
            this.binding.layoutSeasonItemSeasonNameTv.text= item.name
            this.binding.layoutSeasonItemItemCl.setOnClickListener {
                eventListener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_season_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(episodeItems[position])
    }

    override fun getItemCount(): Int = episodeItems.size

    interface EventListener {
        fun click(item: Episode)
    }
}