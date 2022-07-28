package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.R.*

@Composable
fun ItemBottomPreview(
    item: ImageItem?,
    onStoreOnSD: ((ImageItem) -> Unit)? = null,
    onStoreOnGallery: ((ImageItem) -> Unit)? = null
) {
    if (item != null) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id =string.storing_info),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.url,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                if (onStoreOnSD != null) {
                    Button(
                        onClick = { onStoreOnSD(item) }
                    ) {
                        Text(text = stringResource(id = string.store_on_sd_card))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (onStoreOnGallery != null) {
                    Button(
                        onClick = { onStoreOnGallery(item) }
                    ) {
                        Text(text = stringResource(id = string.store_on_gallery))
                    }
                }
            }
        }
    } else {
        // It needs something for when the item is null, or the Bottom Sheet will crash in this case
        Spacer(modifier = Modifier.height(1.dp))
    }
}