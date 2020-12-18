package com.example.android.flowersshop.common

data class Event<out T>(val status: Status, val data: T?, val error: String?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data:T): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?): Event<T> {
            return Event(Status.ERROR, null, message)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}