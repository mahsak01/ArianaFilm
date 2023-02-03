package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.User

data class GetRegisterUserResultInformation(
    val username: List<String?>?,
    val access_token: String?,
    val refresh_token: String?,
    val user: User?,
    val message:String?,
    val detail: String?

)