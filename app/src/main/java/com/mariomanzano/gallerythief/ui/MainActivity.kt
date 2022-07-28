package com.mariomanzano.gallerythief.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.gallerythief.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()

            GalleryThiefApp(
                onStoreOnSD = { image, context ->
                    scope.launch {
                        context.saveImageToDevice(image.url)
                    }
                    Toast.makeText(context.applicationContext, getString(R.string.location_image_stored_info), Toast.LENGTH_LONG).show()
                },
                onStoreOnGallery = { _, context ->
                    Toast.makeText(context.applicationContext, getString(R.string.gallery_storing_message), Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}