package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Live
import com.mgpersia.androidbox.databinding.LayoutLiveItemChannelBinding


class LiveItemChannelAdapter(
    private val liveItems: List<Live>,
    private val show_name: Boolean,
    private val eventListener: EventListener,
    private val context: Context
) : RecyclerView.Adapter<LiveItemChannelAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutLiveItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(live: Live) {
            this.binding.layoutLiveItemChannelNameTv.text = live.en_name
            Picasso.with(context).load(live.icon)
                .transform(RoundedTransformation(15, 0))
                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                .error(context.resources.getDrawable(R.drawable.background_defult_item))
                .fit().centerCrop()
                .into(this.binding.layoutLiveItemChannelImageIv)

            if (!show_name)
                this.binding.layoutLiveItemChannelNameTv.visibility = View.GONE

            this.binding.layoutLiveItemChannelItemCl.setOnClickListener {
                eventListener.click(live)
            }

            this.binding.layoutLiveItemChannelItemCl.setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if (isFocused) {
                    this.binding.layoutLiveItemChannelItemCl.background =
                        context.resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    this.binding.layoutLiveItemChannelItemCl.background =
                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                    this.binding.layoutLiveItemChannelItemCl.setPadding(0, 10, 0, 0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_live_item_channel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(liveItems[position])
    }

    override fun getItemCount(): Int = liveItems.size

    interface EventListener {
        fun click(live: Live)
    }
}