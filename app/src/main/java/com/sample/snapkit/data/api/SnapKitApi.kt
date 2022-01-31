package com.sample.snapkit.data.api

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sample.snapkit.BuildConfig
import com.sample.snapkit.data.models.QueryResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface SnapKitApi {

    @POST("graphql")
    suspend fun searchStickers(@Body body: String): QueryResponse


    companion object {
        private const val TIMEOUT_SECONDS = 60L
        private const val HEADER_AUTHORIZATION = "Authorization"

        fun create(): SnapKitApi {
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
            }.build().create(SnapKitApi::class.java)
        }
    }
}