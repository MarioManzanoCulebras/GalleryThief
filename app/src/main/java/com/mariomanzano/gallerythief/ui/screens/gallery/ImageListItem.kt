package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.R

@Composable
fun ImageListItem(
    item: ImageItem,
    onItemMore: (ImageItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Card(
            elevation = 12.dp
        ) {
            Column {
                ImageWithLoader(urlImage = item.url)
                FooterRow(item = item, onItemMore = onItemMore)
            }
        }

    }
}

@Composable
fun FooterRow(item: ImageItem, onItemMore: (ImageItem) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(colorResource(id = R.color.grey))
            .clickable {
                onItemMore(item)
            }
    ) {
        Text(
            text = item.url,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .weight(1f)
        )
        IconButton(onClick = { onItemMore(item) }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options"
            )
        }
    }
}