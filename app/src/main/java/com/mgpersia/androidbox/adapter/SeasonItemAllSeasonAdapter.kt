package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Season
import com.mgpersia.androidbox.databinding.LayoutAllSeasonBannerItemBinding

class SeasonItemAllSeasonAdapter(
    private val context: Context,
    private val seasonItems: List<Season>,
    private val eventListener: EventListener
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null

//    inner class ViewHolder(val binding: LayoutAllSeasonBannerItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun binding(season: Season) {
//            this.binding.layoutAllSeasonBannerItemNameTv.text = season.name_en
//            Picasso.with(context).load("https://trello.com/1/cards/637f1312b9c1c6010ffc0fa8/attachments/638d94762592f801a573784c/previews/638d94772592f801a5737855/download/2022-12-05_10.09.2.jpg")
//                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
//                .error(context.resources.getDrawable(R.drawable.background_defult_item))
//                .fit().centerCrop()
//                .transform(RoundedTransformation(30, 0))
//                .into(this.binding.layoutAllSeasonBannerItemImageIv)
//
//            this.binding.layoutAllSeasonBannerItemItemCl.setOnClickListener {
//                eventListener.click(season)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.layout_all_season_banner_item,
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding(seasonItems[position])
//    }
//
//    override fun getItemCount(): Int = seasonItems.size

    interface EventListener {
        fun click(season: Season)
    }

    override fun getCount(): Int = seasonItems.size

    override fun getItem(p0: Int): Any = seasonItems[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        if (layoutInflater == null) {
            layoutInflater =
                p2!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.layout_all_season_banner_item, null)
        }
        val season = getItem(p0) as Season
        convertView!!.findViewById<TextView>(R.id.layoutAllSeasonBannerItem_nameTv).text =
            season.name_en
        Picasso.with(context)
            .load("https://trello.com/1/cards/637f1312b9c1c6010ffc0fa8/attachments/638d94762592f801a573784c/previews/638d94772592f801a5737855/download/2022-12-05_10.09.2.jpg")
            .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
            .error(context.resources.getDrawable(R.drawable.background_defult_item))
            .fit().centerCrop()
            .transform(RoundedTransformation(30, 0))
            .into(convertView.findViewById<ImageView>(R.id.layoutAllSeasonBannerItem_imageIv))

        convertView.findViewById<ConstraintLayout>(R.id.layoutAllSeasonBannerItem_itemCl)
            .setOnClickListener {
                eventListener.click(season)
            }
        return convertView
    }
}