package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    listState: LazyGridState,
) {
    if (error != null && !loading && (items == null || items.isEmpty())) {
        if (error is Error.NoData ) ErrorMessage(error = error) else ErrorMessage(
            error = error,
            onRefresh
        )
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
    listState: LazyGridState,
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
                    columns = GridCells.Adaptive(180.dp),
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