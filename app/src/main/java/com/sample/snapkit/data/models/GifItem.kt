package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class GifItem(
    @SerializedName("gifURL")
    val gifURL: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("itemType")
    val itemType: String,
    @SerializedName("mp4URL")
    val mp4URL: String,
    @SerializedName("thumbnailGifURL")
    val thumbnailGifURL: String
)