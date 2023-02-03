package com.mgpersia.androidbox.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.General


class CategoryItemAdapter(
    private val categoryItems: List<General>,
    private val eventListener: EventListener,
    private val context: Context,
    private val size: Int,
    private val nextFocusLeftId: Int = -1,
    private val nextFocusRightId: Int = -1,
    private val nextFocusUpId: Int = -1,
    private val nextFocusDownId: Int = -1,
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    interface EventListener {
        fun click(general: General)
    }

    override fun getCount(): Int = categoryItems.size
    override fun getItem(p0: Int): Any = categoryItems[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        if (layoutInflater == null) {
            layoutInflater =
                p2!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.layout_category_item, null)
        }
        val general = getItem(p0) as General
        Picasso.with(p2!!.context).load(general.cover)
            .transform(RoundedTransformation(15, 0))
            .fit().centerCrop()
            .into(convertView!!.findViewById<ImageView>(R.id.layoutCategoryItem_generalIv))

        general.name.also {
            convertView.findViewById<TextView>(R.id.layoutCategoryItem_generalPTextIv).text = it
        }
        general.name_en.also {
            convertView.findViewById<TextView>(R.id.layoutCategoryItem_generalETextIv).text = it
        }
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
//        if (general.first_color != null && general.second_color != null)
//            drawable.colors = intArrayOf(
//                Color.parseColor(("#3B" + general.first_color.replace("#", ""))),
//                Color.parseColor(("#3B" + general.second_color.replace("#", ""))),
//            )
        drawable.cornerRadius = 15F
        drawable.setGradientCenter(0.3f, 0f)
        drawable.gradientType = GradientDrawable.SWEEP_GRADIENT
        drawable.also {
            convertView.findViewById<ImageView>(R.id.layoutCategoryItem_generalIv).foreground = it
        }
        convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot)
            .setOnClickListener {
                eventListener.click(general)
            }

//        convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot)
//            .setOnFocusChangeListener { view, isFocused ->
//                // add focus handling logic
//                if (isFocused) {
//                    convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot).background =
//                        context.resources.getDrawable(R.drawable.background_select_android_tv)
//                } else {
//                    convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot).background =
//                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
//                }
//            }
//
//        if (nextFocusUpId != -1 && p0 < size)
//            convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot).nextFocusUpId = nextFocusUpId
//
//        if (nextFocusDownId != -1 && p0>(categoryItems.size/size)*size)
//            convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot).nextFocusDownId = nextFocusDownId
//
//        if (p0 == categoryItems.size - 1)
//            if (nextFocusLeftId != -1)
//                convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot).nextFocusLeftId = nextFocusLeftId
//
//        if (p0 == 0)
//            if (nextFocusRightId != -1)
//                convertView.findViewById<ConstraintLayout>(R.id.layoutCategoryItem_generalClRoot).nextFocusRightId = nextFocusRightId

        return convertView
    }
}