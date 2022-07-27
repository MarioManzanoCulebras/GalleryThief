package com.mariomanzano.gallerythief.usecases

import com.mariomanzano.gallerythief.repositories.GalleryThiefRepository
import javax.inject.Inject

class GetImageListUseCase @Inject constructor(private val repository: GalleryThiefRepository) {

    operator fun invoke() = repository.imageList
}