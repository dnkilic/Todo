package com.dnkilic.todo.detail.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedDateViewModel : ViewModel() {
    val time: MutableLiveData<String> = MutableLiveData()
}