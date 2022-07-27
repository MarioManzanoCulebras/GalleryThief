package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.mariomanzano.gallerythief.ui.screens.common.ThiefIcon
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ImageWithLoader(
    urlImage: String?,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null
) {
    GlideImage(
        imageModel = urlImage,
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White
                        )
                    )
                    .aspectRatio(1f)
            )
        },
        failure = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painterResource(ThiefIcon.ThiefAngry.resourceId),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Error loading!",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        })
}