package com.akumakeito.domain.state

sealed class AppState<out T> {

    data object Loading : AppState<Nothing>()
    data class Success<T>(val data: T) : AppState<T>()
    data object Error : AppState<Nothing>()

}