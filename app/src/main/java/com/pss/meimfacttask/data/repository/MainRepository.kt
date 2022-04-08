package com.pss.meimfacttask.data.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pss.meimfacttask.data.api.GiphyApi
import com.pss.meimfacttask.data.datasource.MainDataSource
import com.pss.meimfacttask.data.model.Data
import com.pss.meimfacttask.data.model.GiphyResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val giphyApi: GiphyApi
) {

    fun getGiphyGifs(): Flow<PagingData<Data>> {
        return Pager(
            PagingConfig(pageSize = 20)) {
            MainDataSource(giphyApi)
        }.flow
    }
}