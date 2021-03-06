package com.pss.giphyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pss.giphyapp.data.remote.api.GiphyApi
import com.pss.giphyapp.data.datasource.MainDataSource
import com.pss.giphyapp.data.remote.model.Data
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