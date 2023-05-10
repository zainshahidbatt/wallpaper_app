package com.example.mywallpaper.presentation.collection.photolist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallpaper.presentation.paging.PagingItem

class PhotoListFooterViewHolder(
    view: View,
    val height: Int
) : RecyclerView.ViewHolder(view) {

    fun bind(data: PagingItem.ItemData) {
        itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }
}
