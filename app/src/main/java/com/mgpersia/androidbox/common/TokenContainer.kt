package com.mgpersia.androidbox.common

object TokenContainer {

    var token: String? = null

    fun update(token: String?) {
        this.token = token
    }
}