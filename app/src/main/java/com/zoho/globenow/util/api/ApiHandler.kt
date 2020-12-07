package com.zoho.globenow.util.api

import android.util.Log
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class ApiHandler {

    companion object {
        private const val TAG = "ApiHandler"
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> T): T? {
        val result = newsApiOutput(call)
        var output: T? = null
        when (result) {
            is Response.Success ->
                output = result.response
            is Response.Failure -> {
                result.exception.localizedMessage?.let { Log.d(TAG, "safeApiCall: Error - $it") }
            }
        }
        return output
    }

    private suspend fun <T : Any> newsApiOutput(call: suspend () -> T): Response<T> {
        return try {
            val response = call.invoke()
            Response.Success(response)
        } catch (e: ConnectException) {
            Response.Failure(e)
        } catch (e: SocketTimeoutException) {
            Response.Failure(e)
        } catch (e: UnknownHostException) {
            Response.Failure(e)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}