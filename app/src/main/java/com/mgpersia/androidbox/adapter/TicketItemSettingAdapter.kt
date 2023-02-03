package com.mgpersia.androidbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.Ticket
import com.mgpersia.androidbox.databinding.LayoutTicketItemSettingBinding

class TicketItemSettingAdapter(
    private val ticketItems: List<Ticket>,
    private val eventListener: EventListener
) :
    RecyclerView.Adapter<TicketItemSettingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutTicketItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(ticket: Ticket) {
            this.binding.layoutTicketItemSettingTitleTv.text = "موضوع: ${ticket.subject}"
            this.binding.layoutTicketItemSettingTitleTv.isSelected = true

            this.binding.layoutTicketItemSettingStatusTv.text = "وضعیت: ${ticket.status}"
            this.binding.layoutTicketItemSettingStatusTv.isSelected = true

            if (ticket.department != null)
                this.binding.layoutTicketItemSettingDepartmentTv.text =
                    "دپارتمان: ${ticket.department!!.name}"

            if (ticket.department == null)
                this.binding.layoutTicketItemSettingDepartmentTv.text = "دپارتمان: -"

            this.binding.layoutTicketItemSettingDepartmentTv.isSelected = true

            this.binding.layoutTicketItemSettingTicketCL.setOnClickListener {
                eventListener.click(ticket)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_ticket_item_setting,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(ticketItems[position])
    }

    override fun getItemCount(): Int = ticketItems.size

    interface EventListener {
        fun click(ticket: Ticket)
    }
}