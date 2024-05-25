package com.timurkhabibulin.testandroid.ui.photoDetailScreen

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.timurkhabibulin.testandroid.R
import com.timurkhabibulin.testandroid.domain.entities.Photo
import com.timurkhabibulin.testandroid.ui.LoadState
import kotlinx.coroutines.launch

class PhotoDetailScreen {

    @Preview(
        showSystemUi = true
    )
    @Composable
    fun PhotoDetailScreenPreview() {
        val state = mutableStateOf(LoadState.Success(Photo.Default))
        PhotoDetailScreen(
            state = state,
            onBack = {},
            onRefresh = {}
        )
    }

    companion object {

        @Composable
        fun PhotoDetailScreen(
            photoId: String,
            onBack: () -> Unit,
            viewModel: PhotoDetailScreenViewModel = hiltViewModel()
        ) {
            LaunchedEffect(key1 = null) {
                viewModel.loadPhoto(photoId)
            }
            PhotoDetailScreen(
                state = viewModel.screenState,
                onBack = onBack,
                onRefresh = {
                    viewModel.loadPhoto(photoId)
                }
            )
        }

        @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
        @Composable
        private fun PhotoDetailScreen(
            state: State<LoadState<Photo>>,
            onBack: () -> Unit,
            onRefresh: () -> Unit,
            modifier: Modifier = Modifier
        ) {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        title = {},
                        navigationIcon = {
                            Icon(
                                modifier = Modifier.clickable { onBack() },
                                painter = painterResource(R.drawable.back),
                                contentDescription = null
                            )
                        }
                    )
                }
            ) { paddingValues ->
                val scope = rememberCoroutineScope()
                val refreshing by derivedStateOf{
                    state.value is LoadState.Loading
                }
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = refreshing,
                    onRefresh = {
                        scope.launch {
                            onRefresh()
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .pullRefresh(pullRefreshState)
                ) {
                    when (val loadState = state.value) {
                        is LoadState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(20.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = stringResource(R.string.error),
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier.padding(20.dp)
                                )
                                Text(
                                    text = loadState.error,
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier.padding(20.dp)
                                )
                            }

                        }

                        is LoadState.Success -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Photo(
                                    photo = loadState.value
                                )
                                PhotoDescription(
                                    photo = loadState.value
                                )
                            }
                        }

                        is LoadState.Loading -> {}
                    }

                    PullRefreshIndicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        refreshing = refreshing,
                        state = pullRefreshState
                    )
                }
            }
        }

        @Composable
        private fun Photo(
            photo: Photo,
            modifier: Modifier = Modifier
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.urls.regular)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .placeholder(
                        ColorDrawable(
                            photo.color?.toColorInt() ?: Color.Black.toArgb()
                        )
                    )
                    .crossfade(250)
                    .build(),
                contentDescription = null,
                modifier = modifier.aspectRatio(1f)
            )
        }

        @Composable
        private fun PhotoDescription(
            photo: Photo,
            modifier: Modifier = Modifier
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = photo.user.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Exif(photo = photo)
            }
        }

        @Composable
        private fun Exif(
            photo: Photo,
            modifier: Modifier = Modifier
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.Start)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    ExifParameter(
                        stringResource(R.string.camera),
                        photo.exif?.model ?: stringResource(R.string.unknown)
                    )
                    ExifParameter(
                        stringResource(R.string.focal_length),
                        photo.exif?.focalLength ?: stringResource(R.string.unknown)
                    )
                    ExifParameter(stringResource(R.string.iso), photo.exif?.iso.toString())
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start,
                ) {
                    ExifParameter(
                        stringResource(R.string.aperture),
                        photo.exif?.aperture ?: stringResource(R.string.unknown)
                    )
                    ExifParameter(
                        stringResource(R.string.exposure),
                        photo.exif?.exposureTime ?: stringResource(R.string.unknown)
                    )
                }
            }
        }

        @Composable
        private fun ExifParameter(
            name: String,
            value: String,
            modifier: Modifier = Modifier
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
