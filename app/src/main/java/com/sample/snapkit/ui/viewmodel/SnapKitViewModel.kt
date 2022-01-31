package com.sample.snapkit.ui.viewmodel

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.snapkit.data.models.BitmojiItem
import com.sample.snapkit.data.models.GifItem
import com.sample.snapkit.data.models.StickerItem
import com.sample.snapkit.data.repository.SnapKitRepository
import com.sample.snapkit.data.util.jobErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.sample.snapkit.data.util.Result

class SnapKitViewModel(private val snapKitRepository: SnapKitRepository) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext
    get() = viewModelScope.coroutineContext + Dispatchers.IO + jobErrorHandler

  private val _bitmojis = MutableLiveData<List<BitmojiItem>>()
  val bitmojiList: LiveData<List<BitmojiItem>> get() = _bitmojis

    private val _gifs = MutableLiveData<List<GifItem>>()
    val gifList: LiveData<List<GifItem>> get() = _gifs

    private val _stickers = MutableLiveData<List<StickerItem>>()
    val stickerList: LiveData<List<StickerItem>> get() = _stickers

    private val _error = MutableLiveData<Result.Error>()
    val error: LiveData<Result.Error> get() = _error

    private val _loading = MutableLiveData<Result.Loading>()
    val loading: LiveData<Result.Loading> get() = _loading

  @WorkerThread
  fun getStickersBySearchTerm(@NonNull body: String) =
    viewModelScope.launch(coroutineContext) {
      snapKitRepository.getStickersBySearch(body = body).collect { result ->
          when (result) {
              is Result.Error -> _error.postValue(result)
              is Result.Loading -> _loading.postValue(result)
              else -> {
                  (result as Result.Success).data?.let { searchStickers ->
                      _bitmojis.postValue(searchStickers.bitmojiResults.items)
                      _gifs.postValue(searchStickers.gifResults.items)
                      _stickers.postValue(searchStickers.stickerResults.items)
                  } ?: _error.postValue(result as Result.Error?)
              }
          }
      }
    }

}

