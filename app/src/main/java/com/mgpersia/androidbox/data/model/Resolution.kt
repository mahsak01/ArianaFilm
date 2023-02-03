package com.mgpersia.androidbox.data.model

data class Resolution(
    val date: String,
    val id: Int,
    val link: String,
    val name: String,
    val name_en: String,
    val resolution: String,
    val subtitles: List<Any>
)