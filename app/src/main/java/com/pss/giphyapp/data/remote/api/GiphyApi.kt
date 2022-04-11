package com.pss.giphyapp.data.remote.api

import com.pss.giphyapp.utils.Utils.END_URL
import com.pss.giphyapp.utils.Utils.GIPHY_API_KEY
import com.pss.giphyapp.data.remote.model.GiphyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET(END_URL)
    suspend fun getGiphyGifs(
        @Query("api_key") key: String = GIPHY_API_KEY,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int,
        @Query("lang") lang: String = "ko",
        @Query("bundle") bundle: String = "clips_grid_picker"
    ): Response<GiphyResponse>
}