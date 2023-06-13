package com.dashkevich.util

import com.dashkevich.util.common.OperationState

fun <T> Result<T>.resultHandler(
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit,
    onEmptyResult: () -> Unit,
) {
    onLoading()
    onFailure {
        onError(it.message.toString())
    }
    onSuccess {
        when (it) {
            is Collection<*> -> {
                if (it.isEmpty()) {
                    onEmptyResult()
                } else {
                    onSuccess(it)
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
}

fun OperationState.stateHandler(
    onLoading: () -> Unit,
    onSuccess: () -> Unit,
    onError: () -> Unit,
    onEmptyResult: () -> Unit,
) {
    when (this) {
        OperationState.EmptyResult -> {
            onEmptyResult()
        }
        OperationState.Success -> {
            onSuccess()
        }
        OperationState.Error -> {
            onError()
        }
        OperationState.Loading -> {
            onLoading()
        }
        OperationState.None -> {}
    }
}