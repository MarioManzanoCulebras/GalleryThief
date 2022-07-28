package com.mariomanzano.gallerythief.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.domain.entities.ImageItem

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun rememberGalleryThiefAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
): GalleryThiefAppState = remember(scaffoldState, navController) {
    GalleryThiefAppState(scaffoldState, navController)
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class GalleryThiefAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    var onStoreOnSD: ((ImageItem, Context) -> Unit)? = null,
    var onStoreOnGallery: ((ImageItem, Context) -> Unit)? = null
)