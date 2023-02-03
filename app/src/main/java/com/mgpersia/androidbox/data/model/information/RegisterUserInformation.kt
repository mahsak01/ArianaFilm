package com.mgpersia.androidbox.data.model.information

data class RegisterUserInformation(
    val phone: String?,
    val token: String?,
    val username: String?,
    val country: String?,
    val first_name: String?,
    val last_name: String?,
    val email: String?,
    val device_name: String?,
    val device_type_id: String?,
    val mac_address: String?
)