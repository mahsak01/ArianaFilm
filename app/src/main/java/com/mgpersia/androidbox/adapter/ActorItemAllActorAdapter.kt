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
import com.mgpersia.androidbox.databinding.LayoutAllActorsItemBinding

class ActorItemAllActorAdapter(
    private val context: Context,
    private val actorItems: List<ActorFilmDetailInformation>,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<ActorItemAllActorAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutAllActorsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(item: ActorFilmDetailInformation) {
            this.binding.layoutActorAllItemPageNameTv.text = item.name_en
            Picasso.with(context).load(item.image_path)
                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                .error(context.resources.getDrawable(R.drawable.background_defult_item))
                .transform(RoundedTransformation(50, 0))
                .fit().centerCrop()
                .into(this.binding.layoutActorAllItemPageImageIv)

            this.binding.layoutActorItemHomePageItemCl.setOnClickListener {
                eventListener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_all_actors_item,
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