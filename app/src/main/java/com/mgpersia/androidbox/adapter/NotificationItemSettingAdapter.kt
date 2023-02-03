package com.mgpersia.androidbox.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.Notification
import com.mgpersia.androidbox.databinding.LayoutNotificationItemSettingBinding

class NotificationItemSettingAdapter(
    private val notificationItems: ArrayList<Notification>,
    private val evenListener: EvenListener
) :
    RecyclerView.Adapter<NotificationItemSettingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutNotificationItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binding(notification: Notification) {
            this.binding.layoutNotificationItemSettingMessageTv.text = notification.message
            this.binding.layoutNotificationItemSettingNotificationCl.setOnClickListener {
                evenListener.click(notification)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_notification_item_setting,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(notificationItems[position])
    }

    override fun getItemCount(): Int = notificationItems.size
    interface EvenListener {
        fun click(notification: Notification)
    }
}