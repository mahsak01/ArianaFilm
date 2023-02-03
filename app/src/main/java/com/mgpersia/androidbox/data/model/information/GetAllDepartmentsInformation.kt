package com.mgpersia.androidbox.data.model.information

import com.mgpersia.androidbox.data.model.Department

data class GetAllDepartmentsInformation(
    val count: Int?,
    val next: Any?,
    val previous: Any?,
    val results: List<Department?>?
)