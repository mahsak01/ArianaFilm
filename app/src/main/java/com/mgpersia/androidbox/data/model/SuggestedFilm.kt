package com.mgpersia.androidbox.data.model

data class SuggestedFilm(
    val cover: String?,
    val genres: List<General>?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val name_en: String?
)