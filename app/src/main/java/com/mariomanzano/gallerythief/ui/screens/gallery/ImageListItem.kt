package com.mariomanzano.gallerythief.ui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mariomanzano.domain.entities.ImageItem

@Composable
fun ImageListItem(
    item: ImageItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card {
            Box {
                ImageWithLoader(urlImage = item.url)
            }
        }
    }
}