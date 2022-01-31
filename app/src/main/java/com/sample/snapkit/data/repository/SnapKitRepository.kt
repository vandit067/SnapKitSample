package com.sample.snapkit.data.repository

import androidx.annotation.WorkerThread
import com.sample.snapkit.data.api.SnapKitApi
import com.sample.snapkit.data.util.Result
import com.sample.snapkit.data.util.ViewState
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class SnapKitRepository(private val snapKitApi: SnapKitApi? = null) {

    @WorkerThread
    suspend fun getStickersBySearch(body: String) = flow {
        try {
            emit(Result.Loading())
            snapKitApi?.searchStickers(body = body)?.data?.sticker?.searchStickers?.let {
                emit(Result.Success(data = it, state = ViewState.SUCCESS))
            } ?: emit(Result.Success(data = null, state = ViewState.EMPTY))
        } catch (e: Exception) {
            emit(Result.Error(e, e.message))
            Timber.e(e)
        }
    }

}