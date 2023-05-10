package com.example.mywallpaper.presentation.paging

sealed class DataSourceMode {
    class LIVEDATA : DataSourceMode()
    class RX : DataSourceMode()
}
