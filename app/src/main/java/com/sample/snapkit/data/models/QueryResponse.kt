package com.sample.snapkit.data.models


import com.google.gson.annotations.SerializedName

data class QueryResponse(
    @SerializedName("data")
    val data: Data
)