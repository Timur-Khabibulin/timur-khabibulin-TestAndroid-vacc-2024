package com.timurkhabibulin.testandroid.domain.entities

import com.google.gson.annotations.SerializedName

data class Links(
    val self: String? = null,
    var html: String? = null,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null,
    val download: String? = null,
    @SerializedName("download_location")
    val downloadLocation: String? = null
)
