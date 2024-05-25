package com.timurkhabibulin.testandroid.ui.photoDetailScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timurkhabibulin.testandroid.domain.entities.Photo
import com.timurkhabibulin.testandroid.domain.useCases.GetPhotoUseCase
import com.timurkhabibulin.testandroid.ui.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailScreenViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
) : ViewModel() {
    private val _screenState = mutableStateOf<LoadState<Photo>>(LoadState.Loading())
    val screenState
        get() = _screenState

    fun loadPhoto(id: String) {
        viewModelScope.launch {
            getPhotoUseCase(id)
                .onSuccess {
                    _screenState.value = LoadState.Success(it)
                }.onFailure {
                    _screenState.value = LoadState.Error(it.message ?: "")
                }
        }
    }
}
