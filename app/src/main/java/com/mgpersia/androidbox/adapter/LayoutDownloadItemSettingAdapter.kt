package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.LocalFilmDownload
import com.mgpersia.androidbox.databinding.LayoutAllFilmsItemBinding
import com.squareup.picasso.Picasso

class LayoutDownloadItemSettingAdapter(
    private val context: Context,
    private val filmItems: List<LocalFilmDownload>,
    private val eventListener: EventListener,
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null


//    inner class ViewHolder(val binding: LayoutAllFilmsItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun binding(item: Image) {
//            this.binding.layoutDownloadItemSettingNameTv.text = item.name_en
//            Picasso.with(context).load(item.image)
//                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
//                .error(context.resources.getDrawable(R.drawable.background_defult_item))
//                .fit().centerCrop()
//                .transform(RoundedTransformation(30, 0))
//                .into(this.binding.layoutDownloadItemSettingImageIv)
//
//            this.binding.layoutDownloadItemSettingItemCl.setOnClickListener {
//                eventListener.click(item)
//            }
//            if (item.is_dubbed != null && item.is_dubbed == true)
//                this.binding.layoutFilmItemHomePageSubTv.text = "دوبله"
//            else if (item.has_subtitle != null && item.has_subtitle == true)
//                this.binding.layoutFilmItemHomePageSubTv.text = "زیرنویس"
//            else
//                this.binding.layoutFilmItemHomePageSubTv.visibility = View.GONE
//            if (item.imdb_id != null)
//                this.binding.layoutDownloadItemSettingImbdTv.text = item.imdb_id
//            else
//                this.binding.layoutDownloadItemSettingImbdTv.visibility = View.GONE
//
//            this.binding.layoutDownloadItemSettingItemCl.setOnFocusChangeListener { view, isFocused ->
//                // add focus handling logic
//                if (isFocused) {
//                    this.binding.layoutDownloadItemSettingItemCl.background =
//                        context.resources.getDrawable(R.drawable.background_select_android_tv)
//                } else {
//                    this.binding.layoutDownloadItemSettingItemCl.background =
//                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
//                    this.binding.layoutDownloadItemSettingItemCl.setPadding(0, 10, 0, 0)
//               //     id++
//                }
//            }
////            if (filmItems.indexOf(item) == id)
////                this.binding.layoutDownloadItemSettingItemCl.requestFocus()
//
//
//            if (filmItems.indexOf(item) == filmItems.size - 1)
//                eventListener.reload()
//        }
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.layout_all_films_item,
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding(filmItems[position])
//    }
//
//    override fun getItemCount(): Int = filmItems.size

    interface EventListener {
        fun play(item: LocalFilmDownload)

    }

    override fun getCount(): Int = filmItems.size
    override fun getItem(p0: Int): Any = filmItems[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        if (layoutInflater == null) {
            layoutInflater =
                p2!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.layout_download_item_setting, null)
        }
        val item = getItem(p0) as LocalFilmDownload
        item.name_en.also {
            convertView?.findViewById<TextView>(R.id.layoutDownloadItemSetting_nameTv)!!.text = it
        }
        Picasso.with(context).load(item.cover)
            .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
            .error(context.resources.getDrawable(R.drawable.background_defult_item))
            .fit().centerCrop()
            .transform(RoundedTransformation(30, 0))
            .into(convertView!!.findViewById<ImageView>(R.id.layoutDownloadItemSetting_imageIv))

        convertView.findViewById<ImageView>(R.id.layoutDownloadItemSetting_playBtn)
            .setOnClickListener {
                eventListener.play(item)
            }
        convertView.findViewById<ConstraintLayout>(R.id.layoutDownloadItemSetting_itemCl)
            .setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if (isFocused) {
                    convertView.findViewById<ConstraintLayout>(R.id.layoutDownloadItemSetting_itemCl).background =
                        context.resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    convertView.findViewById<ConstraintLayout>(R.id.layoutDownloadItemSetting_itemCl).background =
                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                    convertView.findViewById<ConstraintLayout>(R.id.layoutDownloadItemSetting_itemCl)
                        .setPadding(0, 10, 0, 0)
                    //     id++
                }
            }
        if (item.isDownload) {
            convertView!!.findViewById<ImageView>(R.id.layoutDownloadItemSetting_playBtn).visibility =
                View.VISIBLE
            convertView!!.findViewById<ProgressBar>(R.id.layoutDownloadItemSetting_progressBar).visibility =
                View.GONE

        } else {
            convertView!!.findViewById<ImageView>(R.id.layoutDownloadItemSetting_playBtn).visibility =
                View.GONE
            convertView!!.findViewById<ProgressBar>(R.id.layoutDownloadItemSetting_progressBar).visibility =
                View.VISIBLE

        }

//            if (filmItems.indexOf(item) == id)
//                this.binding.layoutDownloadItemSettingItemCl.requestFocus()


        return convertView

    }

}