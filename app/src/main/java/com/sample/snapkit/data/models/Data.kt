package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("sticker")
    val sticker: Sticker
)