package com.akumakeito.domain.state

sealed class ResultState<T> {

    data class Success<T>(val data: T) : ResultState<T>()
    data object Error : ResultState<Nothing>()

}