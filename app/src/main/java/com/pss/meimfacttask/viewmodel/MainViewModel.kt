package com.pss.meimfacttask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pss.meimfacttask.Utils
import androidx.lifecycle.viewModelScope
import com.pss.meimfacttask.data.api.GiphyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val giphyApi: GiphyApi
) : ViewModel() {

    fun getGiphyGifs()  = viewModelScope.launch {
        giphyApi.getGiphyGifs(limit = 10, offset = 0).let { response ->
            if (response.isSuccessful){
                Log.d("TAG","API success response : ${response.body()?.data}")
            }else{
                Log.d("TAG","API error response : ${response.errorBody()}")
            }
        }
    }
}