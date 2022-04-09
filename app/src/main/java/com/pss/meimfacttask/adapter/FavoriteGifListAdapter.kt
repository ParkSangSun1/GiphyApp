package com.pss.meimfacttask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pss.meimfacttask.R
import com.pss.meimfacttask.databinding.GiphyListItemBinding
import com.pss.meimfacttask.di.GlideApp
import com.pss.meimfacttask.viewmodel.MainViewModel

class FavoriteGifListAdapter(
    private val mainViewModel: MainViewModel,
    private val context: Context
)
    : RecyclerView.Adapter<FavoriteGifListAdapter.FavoriteGifListViewHolder>() {


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
        val item = mainViewModel.favoriteGifList[position]
        GlideApp.with(context).asGif().load(item.url)
            .into(holder.binding.gifImgView)
    }

    override fun getItemCount(): Int {
        return mainViewModel.favoriteGifList.size
    }

    inner class FavoriteGifListViewHolder(val binding: GiphyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}