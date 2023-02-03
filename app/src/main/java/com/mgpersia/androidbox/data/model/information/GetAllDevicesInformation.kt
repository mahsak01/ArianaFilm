package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Device

data class GetAllDevicesInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Device?>?
)