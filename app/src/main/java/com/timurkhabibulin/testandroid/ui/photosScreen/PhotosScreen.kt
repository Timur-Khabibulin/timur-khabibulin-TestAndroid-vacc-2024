package com.timurkhabibulin.testandroid.ui.photosScreen

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.timurkhabibulin.testandroid.R
import com.timurkhabibulin.testandroid.domain.entities.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PhotosScreen {

    @Preview(
        showSystemUi = true
    )
    @Composable
    private fun PhotoScreenPreview() {
        PhotosScreen(
            photosFlow = flow {
                PagingData.empty<Photo>()
            },
            onPhotoClick = {}
        )
    }

    companion object {

        @Composable
        fun PhotosScreen(
            onPhotoClick: (Photo) -> Unit,
            viewModel: PhotosScreenViewModel = hiltViewModel()
        ) {
            PhotosScreen(
                photosFlow = viewModel.photos,
                onPhotoClick = onPhotoClick
            )
        }

        @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
        @Composable
        private fun PhotosScreen(
            photosFlow: Flow<PagingData<Photo>>,
            onPhotoClick: (Photo) -> Unit,
            modifier: Modifier = Modifier
        ) {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.photos),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                }
            ) { paddingValues ->
                val photos = photosFlow.collectAsLazyPagingItems()
                val scope = rememberCoroutineScope()
                val refreshing by derivedStateOf {
                    photos.loadState.refresh is LoadState.Loading
                }
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = refreshing,
                    onRefresh = {
                        scope.launch {
                            photos.refresh()
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(10.dp)
                        .pullRefresh(pullRefreshState)
                ) {
                    when (val loadState = photos.loadState.refresh) {
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
                                    text = loadState.error.message ?: "",
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier.padding(20.dp)
                                )
                            }
                        }

                        is LoadState.NotLoading -> {
                            LazyVerticalStaggeredGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = StaggeredGridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalItemSpacing = 10.dp
                            ) {
                                items(photos.itemCount) { index ->
                                    photos[index]?.let { photo ->
                                        PhotoCard(
                                            photo = photo,
                                            onPhotoClick = onPhotoClick
                                        )
                                    }
                                }
                            }
                        }

                        LoadState.Loading -> {}
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
        private fun PhotoCard(
            photo: Photo,
            onPhotoClick: (Photo) -> Unit,
            modifier: Modifier = Modifier
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onPhotoClick(photo) }
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(size = 20.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = photo.user.name,
                    style = MaterialTheme.typography.titleMedium,
                    minLines = 2,
                    maxLines = 2
                )
            }
        }
    }
}
