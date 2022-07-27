package com.mariomanzano.gallerythief.usecases

import com.mariomanzano.gallerythief.repositories.GalleryThiefRepository
import javax.inject.Inject

class StealWebImagesListUseCase @Inject constructor(private val repository: GalleryThiefRepository) {

    suspend operator fun invoke(url:String) = repository.stealWebImagesList(url)
}