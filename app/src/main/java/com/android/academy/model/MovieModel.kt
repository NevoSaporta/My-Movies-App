package com.android.academy.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (
    val name :String,
    @DrawableRes val imageRes: Int,
    val overview: String ="",
    val url: String ="",
    @DrawableRes val backgroundRes: Int
):Parcelable