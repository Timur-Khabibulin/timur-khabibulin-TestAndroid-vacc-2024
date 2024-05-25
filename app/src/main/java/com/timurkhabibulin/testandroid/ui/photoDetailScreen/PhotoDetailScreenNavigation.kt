package com.timurkhabibulin.testandroid.ui.photoDetailScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object PhotoDetailScreenNavigation {
    private val name = PhotoDetailScreen.toString()
    private const val PHOTO_ID_PARAM = "photoId"
    private val path =
        "$name?$PHOTO_ID_PARAM={$PHOTO_ID_PARAM}"

    fun NavController.navigateToPhotoDetailScreen(
        photoId: String
    ) {
        val path = "$name?$PHOTO_ID_PARAM=$photoId"
        navigate(path)
    }

    fun NavGraphBuilder.photoDetailScreen(
        onBack: () -> Unit
    ) {
        composable(
            path,
            arguments = listOf(
                navArgument(PHOTO_ID_PARAM) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getString(PHOTO_ID_PARAM) ?: ""
            PhotoDetailScreen.PhotoDetailScreen(
                photoId = photoId,
                onBack = onBack
            )
        }
    }
}
