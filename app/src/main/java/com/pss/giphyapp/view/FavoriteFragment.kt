package com.pss.giphyapp.view

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.pss.giphyapp.R
import com.pss.giphyapp.adapter.FavoriteGifListAdapter
import com.pss.giphyapp.data.db.FavoriteGifDatabase
import com.pss.giphyapp.databinding.FragmentFavoriteBinding
import com.pss.giphyapp.extension.showGrid
import com.pss.giphyapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var adapter : FavoriteGifListAdapter

    override fun init() {
        adapter = FavoriteGifListAdapter(mainViewModel, requireContext())
        observeViewModel()
        getFavoriteGifList()
    }

    //favorite list 가져오기
    private fun getFavoriteGifList(){
        val db = FavoriteGifDatabase.getInstance(requireContext())
        lifecycleScope.launch(Main) {
            withContext(IO){
               mainViewModel.favoriteGifList.postValue(db!!.favoriteGifDao().favoriteGifAllSelectAndGet())
            }
            Log.d("로그","좋아요 불러온 값 : ${mainViewModel.favoriteGifList}")
            initRecyclerView()
        }
    }

    private fun observeViewModel(){
        mainViewModel.favoriteGifList.observe(this, Observer{
            Log.d("로그","데이터 변동 : $it")
            adapter.submitList(it)
        })
    }

    private fun initRecyclerView(){

        binding.favoriteRecyclerView.adapter = adapter
        binding.favoriteRecyclerView.showGrid(requireContext())
        adapter.submitList(mainViewModel.favoriteGifList.value)
        binding.favoriteRecyclerView.setHasFixedSize(true)
    }
}