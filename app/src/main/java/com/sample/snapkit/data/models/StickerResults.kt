package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class StickerResults(
    @SerializedName("items")
    val items: List<StickerItem>
)