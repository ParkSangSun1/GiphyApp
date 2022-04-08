package com.pss.meimfacttask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pss.meimfacttask.Utils
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pss.meimfacttask.data.api.GiphyApi
import com.pss.meimfacttask.data.model.Data
import com.pss.meimfacttask.data.model.GiphyResponse
import com.pss.meimfacttask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getGiphyGifs(): Flow<PagingData<Data>> {
        return mainRepository.getGiphyGifs().cachedIn(viewModelScope)
    }
}