package com.mgpersia.androidbox.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.data.model.Device
import com.mgpersia.androidbox.databinding.LayoutDeviceItemSettingBinding

class DeviceItemSettingAdapter(private val devicesItems: List<Device>,private val resources: Resources,private val eventListener: EventListener) :
    RecyclerView.Adapter<DeviceItemSettingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutDeviceItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun binding(device: Device) {
            this.binding.layoutDeviceItemSettingNameDeviceTv.text=device.name
            this.binding.layoutDeviceItemSettingMacAddressTv.text=device.mac_address

            this.binding.layoutDeviceItemSettingDeleteDeviceBtn.setOnClickListener {
              eventListener.deleteDevice(device)
            }
            this.binding.layoutDeviceItemSettingImageview.setImageDrawable(resources.getDrawable(when(device.device_type){
                1-> R.drawable.ic_phone_device
                2->R.drawable.ic_pc_device
                else -> R.drawable.ic_tv_device
            }))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_device_item_setting,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(devicesItems[position])
    }

    override fun getItemCount(): Int = devicesItems.size

    interface EventListener{
        fun deleteDevice(device: Device)
    }
}