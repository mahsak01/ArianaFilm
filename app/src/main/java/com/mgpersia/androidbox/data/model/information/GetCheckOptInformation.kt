package com.mgpersia.androidbox.data.model.information

data class GetCheckOptInformation(
    val access_token: String?,
    val refresh_token: String?,
    val message: String?,
    val token: String?,
    val detail:String?
)