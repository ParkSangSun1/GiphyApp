package com.pss.giphyapp.extension

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//Vertical RecyclerView
fun RecyclerView.showVertical(context: Context){
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

//Grid RecyclerView
fun RecyclerView.showGrid(context: Context){
    layoutManager = GridLayoutManager(
        context, 2
    )
}