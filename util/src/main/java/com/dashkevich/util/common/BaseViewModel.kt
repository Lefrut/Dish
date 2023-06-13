package com.dashkevich.util.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<M> : ViewModel() {

    private val initialState: M by lazy { setModel() }

    private val _viewState: MutableStateFlow<M> = MutableStateFlow(initialState)
    val viewState: StateFlow<M> = _viewState

    abstract fun setModel(): M

    protected fun setState(reducer: M.() -> M) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }


}