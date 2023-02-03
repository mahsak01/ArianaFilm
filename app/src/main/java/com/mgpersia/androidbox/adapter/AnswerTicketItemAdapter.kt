package com.mgpersia.androidbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.AnswerTicket
import com.mgpersia.androidbox.databinding.LayoutAnswerTicketItemBinding

class AnswerTicketItemAdapter(private val comments: List<AnswerTicket>) :
    RecyclerView.Adapter<AnswerTicketItemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutAnswerTicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(answerTicketItem: AnswerTicket) {
            if (answerTicketItem.staff.equals(UserContainer.user!!.email))
            this.binding.layoutAnswerTicketsAuthorTv.text = "پاسخ شما"
            else
                this.binding.layoutAnswerTicketsAuthorTv.text = "پاسخ ادمین"

            this.binding.layoutAnswerTicketsTextTv.text = answerTicketItem.message


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_answer_ticket_item,
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