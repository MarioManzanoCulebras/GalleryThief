package com.mariomanzano.gallerythief.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryThiefDao {
    @Query("SELECT * FROM DbImage")
    fun getAllImages(): Flow<List<DbImage>>

    @Query("SELECT COUNT(id) FROM DbImage")
    suspend fun getImagesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagesOnDb(items: List<DbImage>)
}