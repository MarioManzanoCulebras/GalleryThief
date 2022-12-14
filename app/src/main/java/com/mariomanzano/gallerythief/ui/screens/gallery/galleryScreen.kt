package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.gallerythief.ui.GalleryThiefAppState

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun GalleryScreen(
    appState: GalleryThiefAppState,
    listState: LazyGridState = rememberLazyGridState(),
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.launchTheRobbery()
    }

    ItemsListScreen(
        appState = appState,
        loading = state.loading,
        items = state.pictures,
        onRefresh = { viewModel.launchTheRobbery() },
        error = state.error,
        listState = listState,
    )
}