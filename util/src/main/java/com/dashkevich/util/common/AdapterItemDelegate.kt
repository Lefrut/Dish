package com.dashkevich.util.common

interface AdapterItemDelegate {

    fun id(): Any

    fun content(): Any

    fun layoutId() : Int = -1

}