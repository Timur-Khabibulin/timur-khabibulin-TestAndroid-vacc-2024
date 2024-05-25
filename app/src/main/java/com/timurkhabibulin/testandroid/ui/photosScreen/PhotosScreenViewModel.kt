package com.timurkhabibulin.testandroid.ui.photosScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.timurkhabibulin.testandroid.domain.useCases.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosScreenViewModel @Inject constructor(
    getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    val photos = getPhotosUseCase().cachedIn(viewModelScope)
}
