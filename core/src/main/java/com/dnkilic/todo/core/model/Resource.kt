package com.dnkilic.todo.core.model

/**
 * Represents a network bound resource. Each subclass represents the resource's state:
 * - [Loading]: the resource is being retrieved from network (current state available in [Loading.loading])
 * - [Success]: the resource has been retrieved (available in [Success.data] field)
 * - [Failure]: the resource retrieving has failed (throwable available in [Failure.throwable]
 * field)
 */
sealed class Resource<out T> {
    data class Loading<out T>(var loading: Boolean) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable) : Resource<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): Resource<T> = Loading(isLoading)
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> failure(e: Throwable): Resource<T> = Failure(e)
    }
}