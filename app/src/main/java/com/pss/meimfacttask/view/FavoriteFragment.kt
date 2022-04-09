package com.pss.meimfacttask.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.pss.meimfacttask.R
import com.pss.meimfacttask.adapter.FavoriteGifListAdapter
import com.pss.meimfacttask.data.db.FavoriteGifDatabase
import com.pss.meimfacttask.databinding.FragmentFavoriteBinding
import com.pss.meimfacttask.di.GlideApp
import com.pss.meimfacttask.extension.showGrid
import com.pss.meimfacttask.viewmodel.MainViewModel
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