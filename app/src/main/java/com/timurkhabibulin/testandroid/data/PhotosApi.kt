package com.timurkhabibulin.testandroid.data

import com.timurkhabibulin.testandroid.domain.entities.Photo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApi {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Photo>

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ): Photo
}
