package com.pss.meimfacttask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pss.meimfacttask.data.remote.model.Data
import com.pss.meimfacttask.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getGiphyGifs(): Flow<PagingData<Data>> {
        return mainRepository.getGiphyGifs().cachedIn(viewModelScope)
    }
}