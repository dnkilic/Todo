package com.dnkilic.todo.core.extension

import androidx.lifecycle.MutableLiveData
import com.dnkilic.todo.core.model.Resource

fun <T> MutableLiveData<Resource<T>>.failed(e: Throwable) {
    with(this){
        loading(false)
        postValue(Resource.failure(e))
    }
}

fun <T> MutableLiveData<Resource<T>>.success(t: T) {
    with(this){
        loading(false)
        postValue(Resource.success(t))
    }
}

fun <T> MutableLiveData<Resource<T>>.loading(isLoading: Boolean) {
    this.postValue(Resource.loading(isLoading))
}