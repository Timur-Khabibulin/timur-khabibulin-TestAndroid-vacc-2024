package com.timurkhabibulin.testandroid.ui

sealed class LoadState<T> {

    class Loading<T> : LoadState<T>()
    class Success<T>(val value: T) : LoadState<T>()
    class Error<Nothing>(val error: String) : LoadState<Nothing>()
}
