package com.mariomanzano.gallerythief.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.gallerythief.ui.GalleryThiefAppState
import com.mariomanzano.gallerythief.ui.screens.home.HomeScreen
import com.mariomanzano.gallerythief.ui.screens.gallery.GalleryScreen

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(appState: GalleryThiefAppState) {
    NavHost(
        navController = appState.navController,
        startDestination = NavCommand.ContentType(Feature.HOME).route
    ) {
        composable(NavCommand.ContentType(Feature.HOME)) {
            HomeScreen(appState = appState)
        }
        composable(NavCommand.ContentTypeByString(Feature.GALLERY)) {
            GalleryScreen()
        }
    }
}

private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ) {
        content(it)
    }
}