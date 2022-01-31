package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class StickerItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("itemType")
    val itemType: String,
    @SerializedName("pngURL")
    val pngURL: String,
    @SerializedName("thumbnailURL")
    val thumbnailURL: String
)