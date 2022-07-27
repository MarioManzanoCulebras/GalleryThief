package com.mariomanzano.gallerythief.repositories

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.datasource.GalleryThiefLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException
import javax.inject.Inject

class GalleryThiefRepository @Inject constructor(
    private val localDataSource: GalleryThiefLocalDataSource
) {
    val imageList = localDataSource.imageList

    suspend fun stealWebImagesList(url: String): Error? = withContext(Dispatchers.IO) {
        val parsedUrl = if (!url.startsWith("http://")) "http://$url" else url
        try {
            val list = mutableListOf<ImageItem>()
            val doc = Jsoup.connect(parsedUrl).get()
            val elements = doc.getElementsByTag("img")
            elements.forEach { img ->
                list.add(ImageItem(
                    id = 0,
                    fromPageUrl = parsedUrl,
                    url = img.absUrl("src")))
            }
            if (list.isEmpty()) return@withContext Error.NoData
            localDataSource.saveImageList(list)
        } catch (e: Exception){
            return@withContext Error.Unknown(e.message?:"Error on parsing Html or Url Malformed")
        }
        return@withContext null
    }
}