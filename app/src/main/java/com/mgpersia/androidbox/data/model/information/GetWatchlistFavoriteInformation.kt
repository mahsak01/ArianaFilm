package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.FavoriteFilmList

data class GetWatchlistFavoriteInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<FavoriteFilmList?>?
)