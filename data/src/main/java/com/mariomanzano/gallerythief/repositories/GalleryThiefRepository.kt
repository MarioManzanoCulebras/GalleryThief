package com.mariomanzano.gallerythief.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.gallerythief.datasource.GalleryThiefLocalDataSource
import javax.inject.Inject

class GalleryThiefRepository @Inject constructor(
    private val localDataSource: GalleryThiefLocalDataSource
) {
    val imageList = localDataSource.imageList

    suspend fun stealWebImagesList(url: String): Error? {
        //Todo: Add logic to parse the Html document from Url
        /*val items = remoteDataSource.findEarthItems()
        items.fold(ifLeft = { return it }) { serverList ->
            if (serverList.isEmpty()) return Error.NoData
            localDataSource.saveImageList(serverList)
        }*/
        return null
    }
}