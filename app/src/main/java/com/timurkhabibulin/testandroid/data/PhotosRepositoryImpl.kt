package com.timurkhabibulin.testandroid.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.timurkhabibulin.testandroid.domain.repository.PhotosRepository
import com.timurkhabibulin.testandroid.domain.entities.Photo
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val photosApi: PhotosApi
) : PhotosRepository {
    override fun getPhotos(pageSize: Int): Pager<Int, Photo> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                ItemsPagingSource { page ->
                    runCatching {
                        photosApi.getPhotos(page, pageSize)
                    }
                }
            }
        )
    }

    override suspend fun getPhoto(id: String): Result<Photo> {
        return runCatching {
            photosApi.getPhoto(id)
        }
    }
}
