package com.dnkilic.todo.detail.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedTagViewModel : ViewModel() {
    val tags: MutableLiveData<MutableList<String>> = MutableLiveData()
}