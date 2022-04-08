package com.pss.meimfacttask.view

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.pss.meimfacttask.R
import com.pss.meimfacttask.adapter.GiphyListAdapter
import com.pss.meimfacttask.databinding.FragmentMainBinding
import com.pss.meimfacttask.extension.showVertical
import com.pss.meimfacttask.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var giphyListAdapter : GiphyListAdapter

    override fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        giphyListAdapter = GiphyListAdapter()

        binding.recyclerView.adapter = giphyListAdapter
        binding.recyclerView.showVertical(requireContext())


        lifecycleScope.launch {
            mainViewModel.getGiphyGifs().collect {
                giphyListAdapter.submitData(it)
            }
        }
    }
}