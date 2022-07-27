package com.mariomanzano.gallerythief.ui.screens.common

import com.mariomanzano.gallerythief.R

sealed class ThiefIcon(val resourceId: Int) {
    object ThiefAngry : ThiefIcon(R.drawable.ic_thief_angry)
    object ThiefGallery : ThiefIcon(R.drawable.ic_thief_gallery)
}