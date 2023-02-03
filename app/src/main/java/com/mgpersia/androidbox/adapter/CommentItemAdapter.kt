package com.mgpersia.androidbox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.Comment
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.*

class CommentItemAdapter(private val comments: List<Comment>,private val context: Context) :
    RecyclerView.Adapter<CommentItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: com.mgpersia.androidbox.databinding.LayoutCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(comment: Comment) {
            this.binding.layoutCommentsTextTv.text = comment.text
            this.binding.layoutCommentsAuthorTv.text=comment.user_id

            val time = comment.date!!.split('-')
            val pdate =
                PersianDate(Date(time[0].toInt(), time[1].toInt(), time[2].split('T')[0].toInt()))
            val pdformater1 = PersianDateFormat("j F y")
            this.binding.layoutCommentsDateTv.text = pdformater1.format(pdate)

            if (!comment.answer.isNullOrEmpty())
                this.binding.layoutCommentsAnswerTv.text = comment.answer
            else
                this.binding.layoutCommentsAnswerCl.visibility = View.GONE

            if (!comment.avatar.isNullOrEmpty())
                Picasso.with(context).load(comment.avatar.toString())
                    .fit().centerCrop()
                    .into(this.binding.layoutCommentsAvatorIv)


            this.binding.layoutCommentsItemCl.setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if (isFocused) {
                    this.binding.layoutCommentsItemCl.background =
                        context.resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    this.binding.layoutCommentsItemCl.background =
                        context.resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                    this.binding.layoutCommentsItemCl.setPadding(0, 10, 0, 0)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_comment_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(comments[position])
    }

    override fun getItemCount(): Int = comments.size
}