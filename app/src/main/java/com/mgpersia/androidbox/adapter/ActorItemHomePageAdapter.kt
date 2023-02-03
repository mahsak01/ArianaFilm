package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.databinding.LayoutActorItemHomePageBinding

class ActorItemHomePageAdapter(
    private val context: Context,
    private val actorItems: List<ActorFilmDetailInformation>,
    private val eventListener: EventListener,
    private val nextFocusLeftId: Int = -1,
    private val nextFocusRightId: Int = -1,
    private val nextFocusUpId: Int=-1,
    private val nextFocusDownId: Int=-1,
) :
    RecyclerView.Adapter<ActorItemHomePageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutActorItemHomePageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: ActorFilmDetailInformation) {
            this.binding.layoutActorItemHomePageNameTv.text = item.name_en
            Picasso.with(context).load(item.image_path)
                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                .error(context.resources.getDrawable(R.drawable.background_defult_item))
                .transform(RoundedTransformation(50, 0))
                .fit().centerCrop()
                .into(this.binding.layoutActorItemHomePageImageIv)

            this.binding.layoutActorItemHomePageItemCl.setOnClickListener {
                eventListener.click(item)
            }
            this.binding.layoutActorItemHomePageItemCl.setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if(isFocused) {
                    this.binding.layoutActorItemHomePageItemCl.background =context.resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    this.binding.layoutActorItemHomePageItemCl.background =context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                    this.binding.layoutActorItemHomePageItemCl.setPadding(0,0,0,0)
                }
            }

            if (nextFocusUpId != -1)
                this.binding.layoutActorItemHomePageItemCl.nextFocusUpId = nextFocusUpId

            if (nextFocusDownId != -1)
                this.binding.layoutActorItemHomePageItemCl.nextFocusDownId = nextFocusDownId

            if (position == actorItems.size - 1)
                if (nextFocusLeftId != -1)
                    this.binding.layoutActorItemHomePageItemCl.nextFocusLeftId = nextFocusLeftId

            if (position == 0)
                if (nextFocusRightId != -1)
                    this.binding.layoutActorItemHomePageItemCl.nextFocusRightId = nextFocusRightId

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_actor_item_home_page,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(actorItems[position])
    }

    override fun getItemCount(): Int = actorItems.size

    interface EventListener {
        fun click(item: ActorFilmDetailInformation)
    }
}