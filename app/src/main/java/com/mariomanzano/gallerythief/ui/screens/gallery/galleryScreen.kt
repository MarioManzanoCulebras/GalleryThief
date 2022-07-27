package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun GalleryScreen(
    listState: LazyListState = rememberLazyListState(),
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    ItemsListScreen(
        loading = state.loading,
        items = state.pictures,
        onRefresh = { viewModel.launchUpdate() },
        error = state.error,
        listState = listState,
    )
}