package com.mariomanzano.gallerythief.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.gallerythief.ui.navigation.Navigation
import com.mariomanzano.gallerythief.ui.theme.GalleryThiefTheme

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GalleryThiefApp(appState: GalleryThiefAppState = rememberGalleryThiefAppState()) {
    GalleryThiefScreen {
        Scaffold { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(appState = appState)
            }
        }
    }
}

@Composable
fun GalleryThiefScreen(content: @Composable () -> Unit) {
    GalleryThiefTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}