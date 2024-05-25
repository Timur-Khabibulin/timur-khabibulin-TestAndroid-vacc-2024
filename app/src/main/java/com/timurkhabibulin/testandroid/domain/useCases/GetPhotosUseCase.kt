package com.timurkhabibulin.testandroid.domain.useCases

import androidx.paging.PagingData
import com.timurkhabibulin.testandroid.domain.repository.PhotosRepository
import com.timurkhabibulin.testandroid.domain.entities.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val PAGE_SIZE = 10

class GetPhotosUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) {
    operator fun invoke(): Flow<PagingData<Photo>> {
        return photosRepository.getPhotos(PAGE_SIZE)
            .flow
            .flowOn(Dispatchers.IO)
    }
}
