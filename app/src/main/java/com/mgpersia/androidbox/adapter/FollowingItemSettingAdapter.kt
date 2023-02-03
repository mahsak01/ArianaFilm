package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Following
import com.mgpersia.androidbox.databinding.LayoutFollowItemSettingBinding

class FollowingItemSettingAdapter(private val followingItems: List<Following>,private val context: Context,private val eventListener:EventListener) :
    RecyclerView.Adapter<FollowingItemSettingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutFollowItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(following: Following) {
            this.binding.layoutFollowingItemSettingActorEnglishNameTv.isSelected=true
            this.binding.layoutFollowingItemSettingActorEnglishNameTv.text= following.actor?.name_en

            this.binding.layoutFollowingItemSettingActorNameTv.isSelected=true
            this.binding.layoutFollowingItemSettingActorNameTv.text= following.actor?.name

            this.binding.layoutFollowingItemSettingActorOccupationTv.isSelected=true
            this.binding.layoutFollowingItemSettingActorOccupationTv.text= following.actor?.occupation

            Picasso.with(context).load(following.actor!!.image_path)
                .transform(RoundedTransformation(15, 0))
                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                .error(context.resources.getDrawable(R.drawable.background_defult_item))
                .fit().centerCrop()
                .into(this.binding.layoutFollowingItemSettingActorIv)

            this.binding.layoutFollowingItemSettingActorCl.setOnClickListener {
                eventListener.click(following)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_follow_item_setting,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(followingItems[position])
    }

    override fun getItemCount(): Int = followingItems.size

    interface EventListener{
        fun click(following: Following)
    }
}