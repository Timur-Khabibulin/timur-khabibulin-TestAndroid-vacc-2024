package com.timurkhabibulin.testandroid.domain.useCases

import com.timurkhabibulin.testandroid.domain.repository.PhotosRepository
import com.timurkhabibulin.testandroid.domain.entities.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val photosRepository: PhotosRepository
) {
    suspend operator fun invoke(id: String): Result<Photo> = withContext(Dispatchers.IO) {
        return@withContext photosRepository.getPhoto(id)
    }
}
