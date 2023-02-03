package com.mgpersia.androidbox.data.model.information

import android.os.Parcelable
import com.mgpersia.androidbox.data.model.Live
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetLiveInformation(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Live?>?
): Parcelable