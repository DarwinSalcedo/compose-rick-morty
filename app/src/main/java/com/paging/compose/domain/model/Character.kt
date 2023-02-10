package com.paging.compose.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Long = 0,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val episode: List<String>
) : Parcelable