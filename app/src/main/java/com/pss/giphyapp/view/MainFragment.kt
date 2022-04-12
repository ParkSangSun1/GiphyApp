package com.pss.giphyapp.view

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.pss.giphyapp.R
import com.pss.giphyapp.adapter.GiphyListAdapter
import com.pss.giphyapp.databinding.FragmentMainBinding
import com.pss.giphyapp.extension.showGrid
import com.pss.giphyapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var giphyListAdapter : GiphyListAdapter

    override fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        giphyListAdapter = GiphyListAdapter(requireContext(), this, mainViewModel)
        binding.recyclerView.adapter = giphyListAdapter
        binding.recyclerView.showGrid(requireContext())
        binding.recyclerView.setHasFixedSize(true)


        lifecycleScope.launch {
            mainViewModel.getGiphyGifs().collect {
                giphyListAdapter.submitData(it)
            }
        }
    }

/*    private fun observeViewModel(){
        mainViewModel.favoriteGifList.observe(this, Observer{
            Log.d("로그","데이터 변동 : $it")
            giphyListAdapter.submitData(it)
        })
    }*/
}