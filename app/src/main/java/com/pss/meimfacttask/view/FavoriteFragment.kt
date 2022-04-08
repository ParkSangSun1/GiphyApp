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
        GlideApp.with(requireContext()).asGif().load("https://media2.giphy.com/media/H2ji6uHAG9aJs7q3WB/giphy.gif?cid=4a972375mohtr8xdjndpih07vytj8s1sj52ak0i3pyyrw5tl&rid=giphy.gif&ct=g").into(binding.img)
    }
}