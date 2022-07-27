package com.mariomanzano.gallerythief.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DbImage(
    @PrimaryKey
    val url: String
)