package com.mariomanzano.gallerythief.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbImage(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val fromPageUrl: String,
    val url: String
)