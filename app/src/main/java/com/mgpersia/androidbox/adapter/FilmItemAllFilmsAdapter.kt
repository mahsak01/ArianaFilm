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
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.LayoutAllFilmsItemBinding
import com.squareup.picasso.Picasso

class FilmItemAllFilmsAdapter(
    private val context: Context,
    private val filmItems: List<Image>,
    private val eventListener: EventListener,
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null


//    inner class ViewHolder(val binding: LayoutAllFilmsItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun binding(item: Image) {
//            this.binding.layoutFilmItemAllFilmPageNameTv.text = item.name_en
//            Picasso.with(context).load(item.image)
//                .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
//                .error(context.resources.getDrawable(R.drawable.background_defult_item))
//                .fit().centerCrop()
//                .transform(RoundedTransformation(30, 0))
//                .into(this.binding.layoutFilmItemAllFilmPageImageIv)
//
//            this.binding.layoutFilmItemAllFilmPageItemCl.setOnClickListener {
//                eventListener.click(item)
//            }
//            if (item.is_dubbed != null && item.is_dubbed == true)
//                this.binding.layoutFilmItemHomePageSubTv.text = "دوبله"
//            else if (item.has_subtitle != null && item.has_subtitle == true)
//                this.binding.layoutFilmItemHomePageSubTv.text = "زیرنویس"
//            else
//                this.binding.layoutFilmItemHomePageSubTv.visibility = View.GONE
//            if (item.imdb_id != null)
//                this.binding.layoutFilmItemAllFilmPageImbdTv.text = item.imdb_id
//            else
//                this.binding.layoutFilmItemAllFilmPageImbdTv.visibility = View.GONE
//
//            this.binding.layoutFilmItemAllFilmPageItemCl.setOnFocusChangeListener { view, isFocused ->
//                // add focus handling logic
//                if (isFocused) {
//                    this.binding.layoutFilmItemAllFilmPageItemCl.background =
//                        context.resources.getDrawable(R.drawable.background_select_android_tv)
//                } else {
//                    this.binding.layoutFilmItemAllFilmPageItemCl.background =
//                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
//                    this.binding.layoutFilmItemAllFilmPageItemCl.setPadding(0, 10, 0, 0)
//               //     id++
//                }
//            }
////            if (filmItems.indexOf(item) == id)
////                this.binding.layoutFilmItemAllFilmPageItemCl.requestFocus()
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
        fun click(item: Image)
        fun reload()

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
            convertView = layoutInflater!!.inflate(R.layout.layout_all_films_item, null)
        }
        val item = getItem(p0) as Image
        item.name_en.also {
            convertView?.findViewById<TextView>(R.id.layoutFilmItemAllFilmPage_nameTv)!!.text = it
        }
        Picasso.with(context).load(item.image)
            .placeholder(context.resources.getDrawable(R.drawable.background_defult_item))
            .error(context.resources.getDrawable(R.drawable.background_defult_item))
            .fit().centerCrop()
            .transform(RoundedTransformation(30, 0))
            .into(convertView!!.findViewById<ImageView>(R.id.layoutFilmItemAllFilmPage_imageIv))

        convertView.findViewById<ConstraintLayout>(R.id.layoutFilmItemAllFilmPage_itemCl)
            .setOnClickListener {
                eventListener.click(item)
            }
        if (item.is_dubbed != null && item.is_dubbed == true)
            convertView.findViewById<TextView>(R.id.layoutFilmItemHomePage_subTv).text = "دوبله"
        else if (item.has_subtitle != null && item.has_subtitle == true)
            convertView.findViewById<TextView>(R.id.layoutFilmItemHomePage_subTv).text = "زیرنویس"
        else
            convertView.findViewById<TextView>(R.id.layoutFilmItemHomePage_subTv).visibility =
                View.GONE
        if (item.imdb_id != null)
            convertView.findViewById<TextView>(R.id.layoutFilmItemAllFilmPage_imbdTv).text =
                item.imdb_id
        else
            convertView.findViewById<TextView>(R.id.layoutFilmItemAllFilmPage_imbdTv).visibility =
                View.GONE

        convertView.findViewById<ConstraintLayout>(R.id.layoutFilmItemAllFilmPage_itemCl).setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                convertView.findViewById<ConstraintLayout>(R.id.layoutFilmItemAllFilmPage_itemCl).background =
                    context.resources.getDrawable(R.drawable.background_select_android_tv)
            } else {
                convertView.findViewById<ConstraintLayout>(R.id.layoutFilmItemAllFilmPage_itemCl).background =
                    context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                convertView.findViewById<ConstraintLayout>(R.id.layoutFilmItemAllFilmPage_itemCl).setPadding(0, 10, 0, 0)
                //     id++
            }
        }
//            if (filmItems.indexOf(item) == id)
//                this.binding.layoutFilmItemAllFilmPageItemCl.requestFocus()


        if (filmItems.indexOf(item) == filmItems.size - 1)
            eventListener.reload()
        return convertView

    }

}