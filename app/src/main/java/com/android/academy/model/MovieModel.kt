package com.android.academy.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (
    val name :String,
    val imageRes: String,
    val overview: String ="",
    val url: String ="",
    val backgroundRes: String
):Parcelable