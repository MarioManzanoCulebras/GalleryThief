package com.mariomanzano.gallerythief.datasource

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.ImageItem
import kotlinx.coroutines.flow.Flow

interface GalleryThiefLocalDataSource {
    val imageList: Flow<List<ImageItem>>

    suspend fun saveImageList(items: List<ImageItem>): Error?
}