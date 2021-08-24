package com.vengateshm.recipesplaza.model

import java.lang.Exception

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    data class Success<out T>(val data: T) : ScreenState<T>()
    data class Error(val exception: Exception) : ScreenState<Nothing>()
}
