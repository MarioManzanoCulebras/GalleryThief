package com.mariomanzano.gallerythief.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [DbImage::class],
    version = 1,
    exportSchema = false
)

abstract class GalleryThiefDatabase : RoomDatabase() {

    abstract fun galleryThiefDao(): GalleryThiefDao
}