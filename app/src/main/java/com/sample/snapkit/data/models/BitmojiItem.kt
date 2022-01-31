package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class BitmojiItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("itemType")
    val itemType: String,
    @SerializedName("webpLargeURL")
    val webpLargeURL: String,
    @SerializedName("webpMediumURL")
    val webpMediumURL: String,
    @SerializedName("webpThumbnailURL")
    val webpThumbnailURL: String
)