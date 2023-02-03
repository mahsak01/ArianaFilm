package com.mgpersia.androidbox.common

import com.mgpersia.androidbox.data.model.User

object UserContainer {

    var user: User? = null

    var is_iran: Boolean = false

    fun update(user: User?) {
        this.user = user
    }
}