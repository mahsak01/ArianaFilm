package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Trailer
import com.mgpersia.androidbox.data.model.*

data class GetFilmDetailInformation(
    val actor: List<ActorFilmDetailInformation>?,
    val age_limit: AgeLimit?,
    val comming_soon: Boolean?,
    val countrys: List<Country>?,
    val cover: String?,
    val cover_height_field: Int?,
    val cover_width_field: Int?,
    val date: String?,
    val description: String?,
    val description_en: String?,
    val director: List<ActorFilmDetailInformation>?,
    val duration: Int?,
    val files: List<Any>?,
    val gallery: List<Gallery>?,
    val genres: List<General>?,
    val has_subtitle: Boolean?,
    val id: Int?,
    val image: String?,
    val imdb: String?,
    val imdb_id: String?,
    var is_bookmark: Boolean?,
    val is_dubbed: Boolean?,
    var is_favorite: Boolean?,
    val is_multi_lang: Boolean?,
    var is_notif: Boolean?,
    val is_series: Boolean?,
    val lang: String?,
    val name: String?,
    val name_en: String?,
    val production_year: Int?,
    val related_films: List<RelatedFilm>?,
    val rottentomatoes: String?,
    val seasons: List<Season>?,
    val suggested_films: List<SuggestedFilm>?,
    val tags: List<Any>?,
    val trailer: List<Trailer>?
)