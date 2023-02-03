package com.mgpersia.androidbox.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Following(
    val actor: Actor?,
    val id: Int?,
    val user: Int?
):Parcelable