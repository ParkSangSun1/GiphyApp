package com.pss.giphyapp.view

import androidx.fragment.app.activityViewModels
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


    override fun init() {
        getFavoriteGifList()
    }

    //favorite list 가져오기
    private fun getFavoriteGifList(){
        val db = FavoriteGifDatabase.getInstance(requireContext())
        lifecycleScope.launch(Main) {
            withContext(IO){
               mainViewModel.favoriteGifList = db!!.favoriteGifDao().favoriteGifAllSelectAndGet()
            }
            initRecyclerView()
        }
    }

    private fun initRecyclerView(){
        binding.favoriteRecyclerView.adapter = FavoriteGifListAdapter(mainViewModel, requireContext())
        binding.favoriteRecyclerView.showGrid(requireContext())
        binding.favoriteRecyclerView.setHasFixedSize(true)
    }
}