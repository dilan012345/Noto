package com.example.noto

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Title: String,
    val Body: String,
    val Bookmarked: Boolean
)  : Parcelable