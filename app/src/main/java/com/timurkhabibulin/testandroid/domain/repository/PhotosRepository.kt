package com.timurkhabibulin.testandroid.domain.repository

import androidx.paging.Pager
import com.timurkhabibulin.testandroid.domain.entities.Photo

interface PhotosRepository {
    fun getPhotos(pageSize: Int): Pager<Int, Photo>

    suspend fun getPhoto(id: String): Result<Photo>
}
