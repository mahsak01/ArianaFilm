package com.mgpersia.androidbox.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.LayoutFilmItemHomePageBinding
import com.squareup.picasso.Picasso

class FilmItemHomePageAdapter(
    private val context: Context,
    private val filmItems: List<Image>,
    private val eventListener: EventListener,
    private val nextFocusLeftId: Int = -1,
    private val nextFocusRightId: Int = -1,
    private val nextFocusUpId: Int=-1,
    private val nextFocusDownId: Int=-1,


    ) :
    RecyclerView.Adapter<FilmItemHomePageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutFilmItemHomePageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun binding(item: Image, position: Int) {
            this.binding.layoutFilmItemHomePageNameTv.text = item.name_en
            Picasso.with(context).load(item.image)
                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
                .error(context.resources.getDrawable(R.drawable.background_defult_item))
                .fit().centerCrop()
                .transform(RoundedTransformation(30, 0))
                .into(this.binding.layoutFilmItemHomePageImageIv)
            this.binding.layoutFilmItemHomePageItemCl.setOnClickListener {
                eventListener.click(item)

            }
            if (item.is_dubbed != null && item.is_dubbed == true)
                this.binding.layoutFilmItemHomePageSubTv.text = "دوبله"
            else if (item.has_subtitle != null && item.has_subtitle == true)
                this.binding.layoutFilmItemHomePageSubTv.text = "زیرنویس"
            else
                this.binding.layoutFilmItemHomePageSubTv.visibility = View.GONE
            if (item.imdb_id != null)
                this.binding.layoutFilmItemAllFilmPageImbdTv.text = item.imdb_id
            else
                this.binding.layoutFilmItemAllFilmPageImbdTv.visibility = View.GONE

            this.binding.layoutFilmItemHomePageItemCl.setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if (isFocused) {
                    this.binding.layoutFilmItemHomePageItemCl.background =
                        context.resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    this.binding.layoutFilmItemHomePageItemCl.background =
                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                    this.binding.layoutFilmItemHomePageItemCl.setPadding(0, 10, 0, 0)
                }
            }
            if (nextFocusUpId != -1)
                this.binding.layoutFilmItemHomePageItemCl.nextFocusUpId = nextFocusUpId

            if (nextFocusDownId != -1)
                this.binding.layoutFilmItemHomePageItemCl.nextFocusDownId = nextFocusDownId

            if (position == filmItems.size - 1)
                if (nextFocusLeftId != -1)
                    this.binding.layoutFilmItemHomePageItemCl.nextFocusLeftId = nextFocusLeftId

            if (position == 0)
                if (nextFocusRightId != -1)
                    this.binding.layoutFilmItemHomePageItemCl.nextFocusRightId = nextFocusRightId

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_film_item_home_page,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(filmItems[position], position)
    }

    override fun getItemCount(): Int = filmItems.size

    interface EventListener {
        fun click(item: Image)

    }
}