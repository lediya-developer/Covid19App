package com.lediya.covid19app.communication.errorhandler

data class ResponseHandler<out T>(val status: Status,
                           val data: T?,
                           val msg: String?) {
    companion object {
        fun <T> success(data: T?): ResponseHandler<T> {
            return ResponseHandler(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): ResponseHandler<T> {
            return ResponseHandler(Status.ERROR, null,message)
        }

        fun <T> loading(data: T? = null): ResponseHandler<T> {
            return ResponseHandler(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}