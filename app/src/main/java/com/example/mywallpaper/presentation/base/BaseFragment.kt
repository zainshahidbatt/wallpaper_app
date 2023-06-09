package com.example.mywallpaper.presentation.base

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import barissaglam.client.wallpaperapp.base.extension.orFalse
import barissaglam.client.wallpaperapp.base.extension.runContextNotNull
import com.example.mywallpaper.R
import com.example.mywallpaper.data.enum.SnackBarType
import com.example.mywallpaper.data.enum.SnackBarType.ERROR
import com.example.mywallpaper.data.enum.SnackBarType.SUCCESS
import com.example.mywallpaper.presentation.view.PlaceholderView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_toolbar.toolbar_container
import kotlinx.android.synthetic.main.layout_toolbar.view.back_iv
import kotlinx.android.synthetic.main.layout_toolbar.view.toolbar_title_tv
import java.io.Serializable

abstract class BaseFragment(
    @LayoutRes private val layoutResId: Int,
    private val hasToolbarBackButton: Boolean = false,
    private val transparentStatusBar: Boolean = false,
    private val hideNavigation: Boolean = false
) : Fragment() {

    protected abstract val viewModel: BaseViewModel?
    protected abstract val toolbarTitle: String?
    protected abstract val placeholderView: PlaceholderView?
    protected abstract val contentView: View?
    private var progressDialog: Dialog? = null

    private val toolbarView: AppBarLayout? by lazy { toolbar_container }

    private var onPermissionGrantedAction: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupSystemBars()
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onStart() {
        super.onStart()

        initViews()
        initListeners()
        initToolbar()
        initViewModelObserving()
        doInitialCalls()
    }

    protected open fun initViews() {}
    protected open fun initListeners() {}
    protected open fun initViewModelObserving() {
        viewModel?.progressState?.observe(this, Observer { progressState ->
            when (progressState) {
                is ProgressState.DONE -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.GONE)
                    contentView?.isVisible = true
                }

                is ProgressState.LOADING -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.LOADING)
                    contentView?.isVisible = false
                }

                is ProgressState.ERROR -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.ERROR)
                    contentView?.isVisible = false
                }

                is ProgressState.INITIAL -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.INITIAL)
                    contentView?.isVisible = false
                }

                is ProgressState.EMPTY -> {
                    placeholderView?.setState(PlaceholderView.PlaceholderState.EMPTY)
                    contentView?.isVisible = false
                }
            }
        })
    }

    protected open fun doInitialCalls() {}

    protected fun navigateBack() {
        Navigation.findNavController(requireView()).popBackStack()
    }

    protected fun navigateTo(
        @IdRes destinationId: Int,
        navigationArgs: List<Pair<String, Serializable>>? = null
    ) {
        navigationArgs?.let { args ->
            val bundle = Bundle()
            args.forEach { bundle.putSerializable(it.first, it.second) }
            Navigation.findNavController(requireView()).navigate(destinationId, bundle)
        } ?: run {
            Navigation.findNavController(requireView()).navigate(destinationId)
        }
    }

    protected fun requestStoragePermissionWithAction(permissionNeededAction: () -> Unit) {
        onPermissionGrantedAction = permissionNeededAction
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
        } else {
            onPermissionGrantedAction?.invoke()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE &&
            grantResults.first() == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGrantedAction?.invoke()
            onPermissionGrantedAction = null
        }
    }

    private fun initToolbar() {
        toolbarView?.let {
            toolbar_container.toolbar_title_tv.text = toolbarTitle ?: ""
            toolbar_container.back_iv.isVisible = hasToolbarBackButton
            if (hasToolbarBackButton) {
                toolbar_container.back_iv.setOnClickListener { navigateBack() }
            }
        }
    }

    private fun setupSystemBars() {
        activity?.window?.statusBarColor = Color.WHITE
        if (hideNavigation) (requireActivity() as BaseActivity).hideNavigation()
        if (transparentStatusBar) {
            requireActivity().window.apply {
                setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
                setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                )
                decorView.systemUiVisibility = 0
            }
            (requireActivity() as BaseActivity).hideNavigation()
        } else {
            requireActivity().window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            }
            if (hideNavigation.not()) (requireActivity() as BaseActivity).showNavigation()
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            requireActivity().window.apply {
                navigationBarColor = getColor(requireContext(), R.color.colorBackground)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                if (transparentStatusBar.not())
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun showSnackBar(
        targetView: View = requireView().findViewById(R.id.root_cl),
        message: String,
        type: SnackBarType
    ) {
        val color = when (type) {
            SUCCESS -> android.R.color.holo_green_dark
            ERROR -> android.R.color.holo_orange_dark
        }
        Snackbar.make(targetView, message, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), color))
            setAction("Close") { dismiss() }
        }.show()
    }


    fun showProgress() {
        if (progressDialog == null) {
            runContextNotNull {
                progressDialog = Dialog(it).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setCancelable(false)
                    setContentView(R.layout.layout_loading_white)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }

        if (!progressDialog?.isShowing.orFalse()) {
            progressDialog?.show()
        }
    }

    fun dismissProgress() {
        if (progressDialog?.isShowing.orFalse()) {
            progressDialog?.dismiss()
        }
    }

    companion object {
        const val PERMISSION_CODE = 223
    }
}
