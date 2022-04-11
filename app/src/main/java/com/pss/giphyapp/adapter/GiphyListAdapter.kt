package com.pss.giphyapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pss.giphyapp.R
import com.pss.giphyapp.data.db.FavoriteGifDatabase
import com.pss.giphyapp.data.db.entity.FavoriteGif
import com.pss.giphyapp.data.remote.model.Data
import com.pss.giphyapp.databinding.GiphyListItemBinding
import com.pss.giphyapp.di.GlideApp
import com.pss.giphyapp.utils.GiphyDiffUtilCallback
import com.pss.giphyapp.viewmodel.MainViewModel
import kotlinx.coroutines.*


class GiphyListAdapter(
    private val context: Context,
    private val mainViewModel: MainViewModel
) :
    PagingDataAdapter<Data, GiphyListAdapter.GiphyListViewHolder>(GiphyDiffUtilCallback()) {
    val db = FavoriteGifDatabase.getInstance(context.applicationContext)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GiphyListAdapter.GiphyListViewHolder {
        return GiphyListViewHolder(
            GiphyListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GiphyListViewHolder, position: Int) {
        val item = getItem(position) ?: return


        CoroutineScope(Dispatchers.IO).launch {
            checkFavoriteGif(holder = holder, item = item)
        }

        GlideApp.with(context).asGif().load(item.images.fixed_width.url)
            .into(holder.binding.gifImgView)
        Log.d("로그", "item : $position")



        holder.binding.heartBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val favoriteState = withContext(Dispatchers.IO) {
                    checkFavoriteGif(holder = holder, item = item)
                }
                Log.d("로그", "클릭 후 favoriteState : $favoriteState")


                //favorite 상태에 따라 toggle 로직
                if (favoriteState) {
                    withContext(Dispatchers.IO) {
                        deleteFavoriteGif(item = item)
                    }
                } else {
                    withContext(Dispatchers.IO) {
                        insertFavoriteGif(item = item)
                    }
                }


                val data = withContext(Dispatchers.IO) {
                    db!!.favoriteGifDao().favoriteGifAllSelectAndGet()
                }

                mainViewModel.callAdapterDataReset(data)

                checkFavoriteGif(holder = holder, item = item)
            }
        }

    }

    //Favorite 눌려있는지 확인
    private suspend fun checkFavoriteGif(holder: GiphyListViewHolder, item: Data): Boolean {
        val favoriteState = CoroutineScope(Dispatchers.Main).async {
            val gifList = withContext(Dispatchers.IO) {
                db!!.favoriteGifDao().favoriteGifSelect(item.id)
            }
            if (gifList.isNullOrEmpty()) {
                holder.binding.heartBtn.setImageResource(R.drawable.heart_thin)
                false
            } else {
                holder.binding.heartBtn.setImageResource(R.drawable.heart)
                true
            }
        }
        return favoriteState.await()
    }

    //favorite 추가
    private fun insertFavoriteGif(item: Data) {
        db!!.favoriteGifDao()
            .favoriteGifInsert(FavoriteGif(gifId = item.id, url = item.images.fixed_width.url))
    }

    //favorite 삭제
    private fun deleteFavoriteGif(item: Data) {
        db!!.favoriteGifDao()
            .favoriteGifDelete(item.id)
    }


    inner class GiphyListViewHolder(val binding: GiphyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}