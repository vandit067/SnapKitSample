package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class SearchStickers(
    @SerializedName("bitmojiResults")
    val bitmojiResults: BitmojiResults,
    @SerializedName("gifResults")
    val gifResults: GifResults,
    @SerializedName("stickerResults")
    val stickerResults: StickerResults
)