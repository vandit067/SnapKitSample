package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class BitmojiResults(
    @SerializedName("items")
    val items: List<BitmojiItem>
)