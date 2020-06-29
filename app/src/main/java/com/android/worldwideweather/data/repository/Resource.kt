package com.android.worldwideweather.data.repository

class Resource<out T: Any> private constructor(
    val status: Status,
    val code: String?,
    val data: T?,
    val message: String?
) {
    companion object {

        fun <T: Any> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                null,
                data,
                null
            )
        }

        fun <T: Any> error(msg: String, code: String? = null, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                code,
                data,
                msg
            )
        }

        fun <T: Any> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                data,
                null
            )
        }
    }
}