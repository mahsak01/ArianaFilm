package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.Comment

data class GetAllCommentOfFilmInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Comment?>?
)