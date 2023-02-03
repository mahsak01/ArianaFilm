package com.mgpersia.androidbox.data.model

data class FavoriteFilmList(
    val films: List<FavoriteFilm?>?,
    val id: Int?,
    val is_favorite: Boolean?,
    val is_bookmark:Boolean?,
    val is_public: Boolean?,
    val name: String?,
    val user_id: String?
)