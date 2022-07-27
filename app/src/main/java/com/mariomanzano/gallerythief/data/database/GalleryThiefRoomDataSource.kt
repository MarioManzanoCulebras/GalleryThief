package com.mariomanzano.gallerythief.data.database

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.datasource.GalleryThiefLocalDataSource
import com.mariomanzano.gallerythief.network.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GalleryThiefRoomDataSource @Inject constructor(private val galleryThiefDao: GalleryThiefDao) : GalleryThiefLocalDataSource {

    override val imageList: Flow<List<ImageItem>> = galleryThiefDao.getAllImages().map { it.toImageDomainModel() }

    override suspend fun saveImageList(items: List<ImageItem>): Error? =
        tryCall {
            galleryThiefDao.insertImagesOnDb(items.fromEarthDomainModel())
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )
}

private fun List<DbImage>.toImageDomainModel(): List<ImageItem> =
    map { it.toDomainModel() }

private fun DbImage.toDomainModel(): ImageItem =
    ImageItem(
        id,
        fromPageUrl = fromPageUrl,
        url = url
    )

private fun List<ImageItem>.fromEarthDomainModel(): List<DbImage> =
    map { it.fromDomainModel() }

private fun ImageItem.fromDomainModel(): DbImage = DbImage(
    id = id,
    fromPageUrl = fromPageUrl,
    url = url,
)
