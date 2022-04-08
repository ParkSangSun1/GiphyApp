package com.pss.meimfacttask.extension

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

//Vertical RecyclerView
fun RecyclerView.showVertical(context: Context){
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.showGrid(context: Context){
    layoutManager = GridLayoutManager(
        context, 2
    )
}