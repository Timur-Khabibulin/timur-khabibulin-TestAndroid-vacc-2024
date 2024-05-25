package com.timurkhabibulin.testandroid.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.timurkhabibulin.testandroid.ui.photoDetailScreen.PhotoDetailScreenNavigation.navigateToPhotoDetailScreen
import com.timurkhabibulin.testandroid.ui.photoDetailScreen.PhotoDetailScreenNavigation.photoDetailScreen
import com.timurkhabibulin.testandroid.ui.photosScreen.PhotosNavigation
import com.timurkhabibulin.testandroid.ui.photosScreen.PhotosNavigation.photosScreen


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PhotosNavigation.path
    ) {
        photosScreen(
            onPhotoClick = {
                navController.navigateToPhotoDetailScreen(it.id)
            }
        )
        photoDetailScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}
