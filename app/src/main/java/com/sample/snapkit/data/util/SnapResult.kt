package com.sample.snapkit.data.util

import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

sealed class Result<T> {
    class Success<T>(val data: T, val state: ViewState) : Result<T>()
    class Loading : Result<Nothing>()
    class Error(
            val e: Throwable,
            val msg: String? = e.message
    ) : Result<Nothing>()
}

enum class ViewState {
    SUCCESS,
    EMPTY,
    ERROR,
    LOADING
}

val jobErrorHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }
