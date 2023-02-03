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
import com.mgpersia.androidbox.data.model.FavoriteFilmList
import com.mgpersia.androidbox.databinding.LayoutSavedFilmGroupItemBinding

class SavedGroupItemSettingAdapter(
    private val savedGroupItems: ArrayList<FavoriteFilmList?>,
    private val context: Context,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<SavedGroupItemSettingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutSavedFilmGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(favoriteFilmList: FavoriteFilmList?, position: Int) {

            val title = if (position == 0) "لیست علاقمندی" else "لیست تماشا"

            this.binding.layoutSavedFilmGroupItemTitleTv.text = title

            if (favoriteFilmList != null && !favoriteFilmList.films.isNullOrEmpty()) {
                this.binding.layoutSavedFilmGroupItemNumberOfItem.text =
                    favoriteFilmList.films!!.size.toString() + " مورد"
                Picasso.with(context).load(favoriteFilmList.films[0]!!.image)
                    .transform(RoundedTransformation(30, 0))
                    .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                    .error(context.resources.getDrawable(R.drawable.background_defult_item))
                    .fit().centerCrop()
                    .into(this.binding.layoutSavedFilmGroupItemItem1Iv)
                if (favoriteFilmList.films.size >= 2)
                    Picasso.with(context).load(favoriteFilmList.films[1]!!.image)
                        .transform(RoundedTransformation(30, 0))
                        .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                        .error(context.resources.getDrawable(R.drawable.background_defult_item))
                        .fit().centerCrop()
                        .into(this.binding.layoutSavedFilmGroupItemItem2Iv)

                if (favoriteFilmList.films.size >= 3)
                    Picasso.with(context).load(favoriteFilmList.films[2]!!.image)
                        .transform(RoundedTransformation(30, 0))
                        .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                        .error(context.resources.getDrawable(R.drawable.background_defult_item))
                        .fit().centerCrop()
                        .into(this.binding.layoutSavedFilmGroupItemItem3Iv)

                if (favoriteFilmList.films.size >= 4)
                    Picasso.with(context).load(favoriteFilmList.films[3]!!.image)
                        .transform(RoundedTransformation(30, 0))
                        .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                        .error(context.resources.getDrawable(R.drawable.background_defult_item))
                        .fit().centerCrop()
                        .into(this.binding.layoutSavedFilmGroupItemItem4Iv)

                this.binding.layoutSavedFilmGroupItemItemCl.setOnClickListener {
                    eventListener.click(favoriteFilmList,title)
                }


            } else {
                this.binding.layoutSavedFilmGroupItemNumberOfItem.text = "0 مورد"
                this.binding.layoutSavedFilmGroupItemItemsIv.visibility = View.GONE
                this.binding.layoutSavedFilmGroupItemEmptyIv.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_saved_film_group_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(savedGroupItems[position], position)
    }

    override fun getItemCount(): Int = savedGroupItems.size

    interface EventListener {
        fun click(favoriteFilmList: FavoriteFilmList, title: String)
    }
}