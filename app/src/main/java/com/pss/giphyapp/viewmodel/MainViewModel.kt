package com.pss.giphyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pss.giphyapp.data.db.entity.FavoriteGif
import com.pss.giphyapp.data.remote.model.Data
import com.pss.giphyapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    var favoriteGifList = MutableLiveData<List<FavoriteGif>>()


    fun getGiphyGifs(): Flow<PagingData<Data>> {
        return mainRepository.getGiphyGifs().cachedIn(viewModelScope)
    }

    fun callAdapterDataReset(data : List<FavoriteGif>){
        favoriteGifList.postValue(data)
    }
}