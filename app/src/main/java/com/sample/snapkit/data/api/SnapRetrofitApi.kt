package com.sample.snapkit.data.api

import com.sample.snapkit.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


class SnapRetrofitApi {

//    @POST("graphql")
//    fun searchStickers(@Body queryPayload: QueryPayload?): Call<QueryResponse?>?


    companion object {
        private const val TIMEOUT_SECONDS = 60L
        private const val HEADER_AUTHORIZATION = "Authorization"

        fun create(): SnapRetrofitApi {
            val client = OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    return@addInterceptor chain.proceed(chain.request().newBuilder().addHeader(
                        HEADER_AUTHORIZATION, "Bearer ${BuildConfig.API_TOKEN}").build())
                }
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logger)
                }
                connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            }.build()
            return Retrofit.Builder().apply {
                baseUrl(BuildConfig.BASE_URL)
                this.client(client)
                addConverterFactory(GsonConverterFactory.create())
//                addCallAdapterFactory(CoroutineCallAdapterFactory())
            }.build().create(SnapRetrofitApi::class.java)
        }
    }
}