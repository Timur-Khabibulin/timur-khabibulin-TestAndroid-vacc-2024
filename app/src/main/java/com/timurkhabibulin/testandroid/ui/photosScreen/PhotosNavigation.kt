package com.timurkhabibulin.testandroid.ui.photosScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.timurkhabibulin.testandroid.domain.entities.Photo

object PhotosNavigation {
    private val name = PhotosScreen.toString()
    val path = name

    fun NavController.navigateToPhotosScreen() {
        navigate(path)
    }

    fun NavGraphBuilder.photosScreen(
        onPhotoClick: (Photo) -> Unit
    ) {
        composable(name) {
            PhotosScreen.PhotosScreen(
                onPhotoClick = onPhotoClick
            )
        }
    }
}
