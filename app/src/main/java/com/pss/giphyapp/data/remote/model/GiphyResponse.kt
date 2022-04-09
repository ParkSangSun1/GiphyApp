package com.pss.giphyapp.data.remote.model

data class GiphyResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)