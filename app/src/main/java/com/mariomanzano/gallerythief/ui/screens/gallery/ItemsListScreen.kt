package com.mariomanzano.gallerythief.ui.screens.gallery

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.ui.GalleryThiefAppState
import com.mariomanzano.gallerythief.ui.screens.common.ErrorMessage
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ItemsListScreen(
    appState: GalleryThiefAppState,
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
        var bottomSheetItem by remember { mutableStateOf<ImageItem?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
               Toast.makeText(context.applicationContext, "Permission Granted, you can now click again to store it", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context.applicationContext, "Permission Denied, you have to granted it to make this action", Toast.LENGTH_LONG).show()
            }
        }

        BackHandler(sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                ItemBottomPreview(
                    context = context,
                    item = bottomSheetItem,
                    onStoreOnSD = { image, context ->
                        scope.launch {
                            sheetState.hide()

                            checkPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                image,
                                appState.onStoreOnSD,
                                launcher
                            )
                        }

                    },
                    onStoreOnGallery  = { image, context ->
                        scope.launch {
                            sheetState.hide()

                            checkPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                image,
                                appState.onStoreOnGallery,
                                launcher
                            )
                        }
                    }
                )
            },
            sheetState = sheetState
        ) {
            GalleryItemsList(
                loading = loading,
                onItemMore = {
                    scope.launch {
                        bottomSheetItem = it
                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                },
                items = items,
                listState = listState
            )
        }
    }
}

private fun checkPermission(
    context: Context,
    permission: String,
    item: ImageItem,
    onStore: ((ImageItem, Context) -> Unit)? = null,
    launcher: ActivityResultLauncher<String>
){
    // Check permission
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) -> {
            onStore?.invoke(item, context)
        }
        else -> {
            launcher.launch(permission)
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun GalleryItemsList(
    loading: Boolean,
    onItemMore: (ImageItem) -> Unit,
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
                            item = it,
                            onItemMore = onItemMore
                        )
                    }
                }
            }
        }
    }
}