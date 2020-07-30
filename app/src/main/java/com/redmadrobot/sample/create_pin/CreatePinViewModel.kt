package com.redmadrobot.sample.create_pin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redmadrobot.pinkman.Pinkman

class CreatePinViewModel @ViewModelInject constructor(private val pinkman: Pinkman) : ViewModel() {

    val pinIsCreated = MutableLiveData<Boolean>()

    fun createPin(pin: String) {
        pinkman.createPin(pin)

        pinIsCreated.postValue(true)
    }
}
