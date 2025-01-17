package com.example.androidtvapp.utils

sealed class DataResult<out R> {
    class Success<out T>(val data: T) : DataResult<T>()
    class Error(val message: String) : DataResult<Nothing>()
}