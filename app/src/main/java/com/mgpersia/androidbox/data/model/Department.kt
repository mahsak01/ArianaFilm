package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Department(
    val description: String?,
    val id: Int?,
    val name: String?
):Parcelable