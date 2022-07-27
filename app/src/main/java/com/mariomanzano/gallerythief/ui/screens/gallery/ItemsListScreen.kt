package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.ui.screens.common.ErrorMessage

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ItemsListScreen(
    loading: Boolean = false,
    items: List<ImageItem>?,
    onRefresh: (() -> Unit)? = null,
    error: Error? = null,
    listState: LazyListState,
) {
    if (error != null && !loading && (items == null || items.isEmpty())) {
        ErrorMessage(error = error, onRefresh)
    } else {
            GalleryItemsList(
                loading = loading,
                items = items,
                listState = listState
            )
    }
}


@ExperimentalFoundationApi
@Composable
fun GalleryItemsList(
    loading: Boolean,
    items: List<ImageItem>?,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (loading) {
            CircularProgressIndicator()
        }

        items?.let { list ->
            if (list.isNotEmpty()) {
                LazyVerticalGrid(
                    state = listState,
                    cells = GridCells.Adaptive(180.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {

                    items(list) {
                        ImageListItem(
                            item = it
                        )
                    }
                }
            }
        }
    }
}