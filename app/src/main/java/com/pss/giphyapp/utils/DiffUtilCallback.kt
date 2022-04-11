package com.pss.giphyapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.pss.giphyapp.data.db.entity.FavoriteGif
import com.pss.giphyapp.data.remote.model.Data

class FavoriteGifDiffUtilCallback(
    private val oldFavoriteGifs: List<FavoriteGif>,
    private val newFavoriteGifs: List<FavoriteGif>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldFavoriteGifs.size
    }

    override fun getNewListSize(): Int {
       return newFavoriteGifs.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteGifs[oldItemPosition] == newFavoriteGifs[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteGifs[oldItemPosition].gifId == newFavoriteGifs[newItemPosition].gifId
    }
}

class GiphyDiffUtilCallback(

) : DiffUtil.ItemCallback<Data>() {


    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id== newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}