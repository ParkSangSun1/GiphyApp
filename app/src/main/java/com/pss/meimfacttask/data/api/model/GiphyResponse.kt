package com.pss.meimfacttask.data.api.model

data class GiphyResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)