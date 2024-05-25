package com.timurkhabibulin.testandroid.domain.entities

import com.google.gson.annotations.SerializedName
import com.timurkhabibulin.domain.entities.User


data class Photo(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val downloads: Int,
    val likes: Int,
    val description: String?,
    val exif: Exif?,
    val urls: Urls,
    val links: Links,
    val user: User
) {
    companion object {
        val Default = Photo(
            "Dwu85P9SOIk",
            "2016-05-03T11:00:28-04:00",
            2448,
            3264,
            "#6E633A",
            1345,
            24,
            "A man drinking a coffee.",
            Exif.Default,
            Urls(),
            Links(),
            User.DefaultUser
        )
    }
}
