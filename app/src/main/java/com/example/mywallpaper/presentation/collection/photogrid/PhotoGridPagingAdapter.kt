package com.example.mywallpaper.presentation.collection.photogrid

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mywallpaper.R
import com.example.mywallpaper.presentation.ext.dpToPix
import com.example.mywallpaper.presentation.ext.inflate
import com.example.mywallpaper.presentation.model.PhotoModel
import com.example.mywallpaper.presentation.paging.PagingAdapter
import com.example.mywallpaper.presentation.paging.PagingItem
import com.example.mywallpaper.presentation.paging.PagingUpdater

class PhotoGridPagingAdapter(
    pagingUpdater: PagingUpdater<PhotoModel>,
    initialLoad: Boolean = false,
    private val photoCardWidth: Int,
    private val photoCardHeight: Int,
    private val photoCardCornerRadius: Int,
    private val onPhotoClickListener: ((photoModel: PhotoModel) -> Unit)? = null
) : PagingAdapter<RecyclerView.ViewHolder, PhotoModel>(
    pagingUpdater,
    DIFF_CALLBACK,
    initialLoad
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var holder: RecyclerView.ViewHolder
        when (viewType) {
            PagingItem.ItemType.DATA.itemCode -> {
                val view = parent.inflate(R.layout.item_photo_in_grid, parent, false)
                holder = PhotoGridDataViewHolder(
                    view,
                    photoCardWidth,
                    photoCardHeight,
                    view.context.dpToPix(photoCardCornerRadius)
                )
            }

            PagingItem.ItemType.HEADER.itemCode -> {
                val view = parent.inflate(R.layout.item_header, parent, false)
                holder = PhotoGridHeaderViewHolder(
                    view,
                    photoCardWidth,
                    photoCardHeight
                )
            }

            PagingItem.ItemType.FOOTER.itemCode -> {
                val view = parent.inflate(R.layout.item_footer, parent, false)
                holder = PhotoGridFooterViewHolder(
                    view,
                    photoCardWidth,
                    photoCardHeight
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            PagingItem.ItemType.DATA.itemCode -> {
                items[position].data?.let { photo ->
                    (holder as PhotoGridDataViewHolder).apply {
                        bind(photo, (position % 3 == 0))
                        setOnClickListener { onPhotoClickListener?.invoke(photo) }
                    }
                }
            }

            PagingItem.ItemType.HEADER.itemCode -> {
                items[position].itemData?.let {
                    (holder as PhotoGridHeaderViewHolder).bind(it)
                }
            }

            PagingItem.ItemType.FOOTER.itemCode -> {
                items[position].itemData?.let {
                    (holder as PhotoGridFooterViewHolder).bind(it, (position % 3 == 0))
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PhotoModel>() {
            override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem.normalPhotoUrl == newItem.normalPhotoUrl
            }
        }
    }
}
