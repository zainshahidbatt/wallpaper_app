package com.example.mywallpaper.presentation.screen.splash

import android.os.Handler
import android.view.View
import com.example.mywallpaper.R
import com.example.mywallpaper.presentation.base.BaseFragment
import com.example.mywallpaper.presentation.base.BaseViewModel
import com.example.mywallpaper.presentation.view.PlaceholderView

class SplashFragment() : BaseFragment(
    layoutResId = R.layout.fragment_splash,
    hideNavigation = true
) {
    override val viewModel: BaseViewModel? = null
    override val toolbarTitle: String? = null
    override val contentView: View? = null
    override val placeholderView: PlaceholderView? = null

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ navigateTo(R.id.homeFragment) }, 2000)
    }
}
