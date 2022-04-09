package com.pss.meimfacttask.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.pss.meimfacttask.R
import com.pss.meimfacttask.databinding.FragmentFavoriteBinding
import com.pss.meimfacttask.di.GlideApp

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {


    override fun init() {
        GlideApp.with(requireContext()).asGif().load("https://media4.giphy.com/media/cnYo9a9IYylHph7QPC/200w.gif?cid=4a972375a963t9rdoj60f8mdxo3e47z8tzfh27lilvgi7r51&rid=200w.gif&ct=g").into(binding.img)
    }
}