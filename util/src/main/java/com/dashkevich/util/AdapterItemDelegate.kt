package com.dashkevich.util

interface AdapterItemDelegate {

    fun id(): Any

    fun content(): Any

    fun layoutId() : Int = -1

}