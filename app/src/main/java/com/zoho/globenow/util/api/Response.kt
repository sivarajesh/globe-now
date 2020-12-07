package com.zoho.globenow.util.api

import java.lang.Exception

sealed class Response<out T : Any> {
    class Success<out T : Any>(val response: T) : Response<T>()
    class Failure(val exception: Exception) : Response<Nothing>()
}