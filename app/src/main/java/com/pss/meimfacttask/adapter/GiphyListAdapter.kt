package com.pss.meimfacttask.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pss.meimfacttask.R
import com.pss.meimfacttask.data.db.FavoriteGifDatabase
import com.pss.meimfacttask.data.db.entity.FavoriteGif
import com.pss.meimfacttask.data.remote.model.Data
import com.pss.meimfacttask.databinding.GiphyListItemBinding
import com.pss.meimfacttask.di.GlideApp
import kotlinx.coroutines.*


class GiphyListAdapter(
    private val context: Context
) :
    PagingDataAdapter<Data, GiphyListAdapter.GiphyListViewHolder>(diffCallback) {
    val db = FavoriteGifDatabase.getInstance(context.applicationContext)


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }


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

                checkFavoriteGif(holder = holder, item = item)
            }
        }

    }

    //Favorite 눌려있는지 확인
    private suspend fun checkFavoriteGif(holder: GiphyListViewHolder, item: Data): Boolean {
        var favoriteState = CoroutineScope(Dispatchers.Main).async {
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