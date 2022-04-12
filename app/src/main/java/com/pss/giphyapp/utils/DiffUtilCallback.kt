package com.pss.giphyapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.pss.giphyapp.data.db.entity.FavoriteGif
import com.pss.giphyapp.data.remote.model.Data

class GiphyDiffUtilCallback(

) : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Data, newItem: Data) =
        oldItem == newItem
}

class FavoriteDiffUtilCallback() : DiffUtil.ItemCallback<FavoriteGif>() {
    override fun areItemsTheSame(oldItem: FavoriteGif, newItem: FavoriteGif) =
        oldItem.gifId == newItem.gifId

    override fun areContentsTheSame(oldItem: FavoriteGif, newItem: FavoriteGif) =
        oldItem == newItem
}