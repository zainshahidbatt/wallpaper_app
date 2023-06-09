package com.example.mywallpaper.presentation.screen.search

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mywallpaper.R
import com.example.mywallpaper.presentation.base.BaseFragment
import com.example.mywallpaper.presentation.collection.photogrid.PhotoGridPagingAdapter
import com.example.mywallpaper.presentation.collection.photogrid.PhotoItemDecoration
import com.example.mywallpaper.presentation.const.Consts
import com.example.mywallpaper.presentation.ext.dpToPix
import com.example.mywallpaper.presentation.ext.getScreenHeightInPixels
import com.example.mywallpaper.presentation.ext.getScreenWidthInPixels
import com.example.mywallpaper.presentation.ext.hideKeyboard
import com.example.mywallpaper.presentation.model.PhotoModel
import com.example.mywallpaper.presentation.view.PlaceholderView
import com.example.mywallpaper.presentation.screen.photo.PhotoFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_toolbar_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(
    layoutResId = R.layout.fragment_search
) {
    override val viewModel by viewModel<SearchViewModel>()
    override val toolbarTitle: String? = null
    override val contentView: View? by lazy { content_ll }
    override val placeholderView: PlaceholderView? by lazy { placeholder_view }

    private val photoCardMargin by lazy { requireActivity().dpToPix(Consts.DEFAULT_MARGIN_DP) }
    private val photoCardWidth by lazy { requireActivity().getScreenWidthInPixels() / 2 }
    private val photoCardHeight by lazy { requireActivity().getScreenHeightInPixels() / 3 }

    private lateinit var photoGridPagingAdapter: PhotoGridPagingAdapter
    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPhotoGridPagingAdapter()
    }

    override fun initViews() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        photo_grid_rv.layoutManager = staggeredGridLayoutManager
        photo_grid_rv.adapter = photoGridPagingAdapter
        if (photo_grid_rv.itemDecorationCount == 0) {
            photo_grid_rv.addItemDecoration(
                PhotoItemDecoration(
                    photoCardMargin.toInt()
                )
            )
        }
    }

    override fun initListeners() {
        search_field_edt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(textView.text.toString())
                requireActivity().hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.photos.observe(this, Observer { newPhoto ->
            photoGridPagingAdapter.items = newPhoto
        })
    }

    override fun onPause() {
        super.onPause()
        photo_grid_rv.layoutManager?.onSaveInstanceState()?.let { state ->
            recyclerState = state
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerState?.let { state ->
            photo_grid_rv.layoutManager?.onRestoreInstanceState(state)
        }
    }

    private fun initPhotoGridPagingAdapter() {
        photoGridPagingAdapter =
            PhotoGridPagingAdapter(
                viewModel.photoGridPagingUpdater,
                false,
                photoCardWidth,
                photoCardHeight,
                Consts.DEFAULT_CORNER_RADIUS_DP
            ) { photo ->
                navigateToFullPhoto(photo)
            }
    }

    private fun navigateToFullPhoto(photoModel: PhotoModel) {
        navigateTo(R.id.photoFragment, listOf(Pair(PhotoFragment.ARG_KEY, photoModel)))
    }
}
