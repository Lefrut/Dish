package com.dashkevich.util

import android.util.Log

fun <T> Result<T>.resultHandler(
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit,
    onEmptyResult: () -> Unit,
) {
    onLoading()
    onSuccess {
        when (it) {
            is Collection<*> -> {
                if (it.isEmpty()) {
                    onEmptyResult()
                }
            }
            null -> {
                onEmptyResult()
            }
            else -> {
                onSuccess(it)
            }
        }
    }
    onFailure {
        onError(it.message.toString())
    }
}