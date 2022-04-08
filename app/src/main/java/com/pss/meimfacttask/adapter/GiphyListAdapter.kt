package com.pss.meimfacttask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pss.meimfacttask.data.model.Data
import com.pss.meimfacttask.databinding.GiphyListItemBinding
import com.pss.meimfacttask.di.GlideApp

class GiphyListAdapter(
    private val context : Context
) :
    PagingDataAdapter<Data, GiphyListAdapter.GiphyListViewHolder>(diffCallback) {


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
        holder.onBind(item)

        GlideApp.with(context).asGif().load(item.images.fixed_width.url).into(holder.binding.gifImgView)
    }


    inner class GiphyListViewHolder(val binding: GiphyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Data) {
            binding.data = data
        }
    }
}