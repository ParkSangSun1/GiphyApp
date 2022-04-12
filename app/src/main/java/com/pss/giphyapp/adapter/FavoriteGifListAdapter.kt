package com.pss.giphyapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pss.giphyapp.R
import com.pss.giphyapp.data.db.FavoriteGifDatabase
import com.pss.giphyapp.data.db.entity.FavoriteGif
import com.pss.giphyapp.data.remote.model.Data
import com.pss.giphyapp.databinding.GiphyListItemBinding
import com.pss.giphyapp.di.GlideApp
import com.pss.giphyapp.utils.FavoriteDiffUtilCallback
import com.pss.giphyapp.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteGifListAdapter(
    private val mainViewModel: MainViewModel,
    private val context: Context
) : ListAdapter<FavoriteGif, FavoriteGifListAdapter.FavoriteGifListViewHolder>(
    FavoriteDiffUtilCallback()
) {
    val db = FavoriteGifDatabase.getInstance(context.applicationContext)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteGifListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<GiphyListItemBinding>(
            layoutInflater,
            R.layout.giphy_list_item,
            parent,
            false
        )
        return FavoriteGifListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteGifListViewHolder, position: Int) {
             val item = getItem(position)
             if (item != null) {
                 holder.binding.heartBtn.setImageResource(R.drawable.heart)
                 GlideApp.with(context).asGif().load(item.url)
                     .into(holder.binding.gifImgView)

                 holder.binding.heartBtn.setOnClickListener {
                     CoroutineScope(Dispatchers.Main).launch {
                         withContext(Dispatchers.IO) {
                             deleteFavoriteGif(id = item.gifId)
                         }

                         val data = withContext(Dispatchers.IO) {
                             db!!.favoriteGifDao().favoriteGifAllSelectAndGet()
                         }

                         mainViewModel.callAdapterDataReset(data)
                         holder.binding.heartBtn.setImageResource(R.drawable.heart_thin)
                     }
                 }

             }
    }

    //favorite 삭제
    private fun deleteFavoriteGif(id: String) {
        db!!.favoriteGifDao()
            .favoriteGifDelete(id)
    }

    inner class FavoriteGifListViewHolder(val binding: GiphyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


}