package com.pss.giphyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagingDataAdapter
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
    private val fragment: Fragment,
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

        observeViewModel(holder, item)

        holder.bind(item)

        //초기 favorite 상태 가져오기
        CoroutineScope(Dispatchers.IO).launch {
            checkFavoriteGifInRoom(binding = holder.binding, item = item)
        }

        //Gif 로드
        GlideApp.with(context).asGif().load(item.images.fixed_width.url)
            .into(holder.binding.gifImgView)
    }

    //Favorite 상태 관찰
    private fun observeViewModel(holder: GiphyListViewHolder, item: Data) {
        mainViewModel.favoriteGifList.observe(fragment, Observer {
            if (checkFavoriteGifInList(item = item)) holder.binding.heartBtn.setImageResource(R.drawable.heart)
            else holder.binding.heartBtn.setImageResource(R.drawable.heart_thin)
        })
    }

    //Favorite 눌려있는지 확인 (ViewModel list 에서)
    private fun checkFavoriteGifInList(item: Data): Boolean {
        var checkId = false
        mainViewModel.favoriteGifList.value?.forEach {
            if (it.gifId == item.id) checkId = true
        }
        return checkId
    }

    //Favorite 눌려있는지 확인 (Room DB)
    private suspend fun checkFavoriteGifInRoom(binding: GiphyListItemBinding, item: Data): Boolean {
        val favoriteState = CoroutineScope(Dispatchers.Main).async {
            val gifList = withContext(Dispatchers.IO) {
                db!!.favoriteGifDao().favoriteGifSelect(item.id)
            }
            if (gifList.isNullOrEmpty()) {
                binding.heartBtn.setImageResource(R.drawable.heart_thin)
                false
            } else {
                binding.heartBtn.setImageResource(R.drawable.heart)
                true
            }
        }
        return favoriteState.await()
    }

    //favorite 추가 (Room DB)
    private fun insertFavoriteGifInRoom(item: Data) {
        db!!.favoriteGifDao()
            .favoriteGifInsert(FavoriteGif(gifId = item.id, url = item.images.fixed_width.url))
    }

    //favorite 삭제 (Room DB)
    private fun deleteFavoriteGifInRoom(item: Data) {
        db!!.favoriteGifDao()
            .favoriteGifDelete(item.id)
    }

    //heart button 클릭 시
    private fun heartBtnClick(binding: GiphyListItemBinding, item: Data) {
        CoroutineScope(Dispatchers.IO).launch {

            //favorite 현재 상태 확인
            val favoriteState = withContext(Dispatchers.IO) {
                checkFavoriteGifInRoom(binding = binding, item = item)
            }

            //favorite 상태에 따라 toggle 로직
            withContext(Dispatchers.IO) {
                if (favoriteState) deleteFavoriteGifInRoom(item = item)
                else insertFavoriteGifInRoom(item = item)
            }

            //순차적으로 삭제/ 추가 후 전체 Room data 가져오기
            val data = withContext(Dispatchers.IO) {
                db!!.favoriteGifDao().favoriteGifAllSelectAndGet()
            }

            //Favorite add item
            mainViewModel.callAdapterDataReset(data)

            checkFavoriteGifInRoom(binding = binding, item = item)
        }
    }

    inner class GiphyListViewHolder(val binding: GiphyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Data?){
            binding.heartBtn.setOnClickListener {
                if (item != null) heartBtnClick(
                    binding = binding,
                    item = item
                )
            }
        }
    }
}