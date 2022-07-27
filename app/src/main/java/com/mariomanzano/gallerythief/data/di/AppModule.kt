package com.mariomanzano.gallerythief.data.di

import android.app.Application
import androidx.room.Room
import com.mariomanzano.gallerythief.data.database.GalleryThiefDatabase
import com.mariomanzano.gallerythief.data.database.GalleryThiefRoomDataSource
import com.mariomanzano.gallerythief.datasource.GalleryThiefLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        GalleryThiefDatabase::class.java,
        "gallery-thief-db"
    ).build()

    @Provides
    @Singleton
    fun provideGalleryThiefDao(db: GalleryThiefDatabase) = db.galleryThiefDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindGalleryThiefLocalDataSource(localDataSource: GalleryThiefRoomDataSource): GalleryThiefLocalDataSource

}