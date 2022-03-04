package com.example.pressreview.constants

open class Event<out T>(private val content:T) {

    var hasBeenHandled = false
    private set

    fun getContentIfNotHandled9(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T= content
}