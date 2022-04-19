package com.example.edvora.listner

interface ApiListener {
    fun success(url: String, response: Any)
    fun error(error: String)
    fun failure(message: String)
}