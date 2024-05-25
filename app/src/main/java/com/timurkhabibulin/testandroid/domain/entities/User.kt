package com.timurkhabibulin.domain.entities

import com.google.gson.annotations.SerializedName
import com.timurkhabibulin.testandroid.domain.entities.Links
import com.timurkhabibulin.testandroid.domain.entities.Photo
import com.timurkhabibulin.testandroid.domain.entities.Urls

data class User(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("total_collection")
    val totalCollection: Int,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("profile_image")
    val profileImage: Urls,
    val links: Links,
    val photos: List<Photo>?
) {
    companion object {
        val DefaultUser = User(
            "QPxL2MGqfrw",
            "exampleuser",
            "Joe Example",
            null,
            "From epic drone shots to inspiring moments" +
                    " in nature — submit your best desktop and mobile backgrounds.",
            "Location",
            5,
            10,
            7,
            3,
            2,
            Urls(),
            Links(),
            emptyList()
        )
    }
}
