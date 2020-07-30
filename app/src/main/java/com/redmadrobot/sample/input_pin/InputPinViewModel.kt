package com.redmadrobot.sample.input_pin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redmadrobot.pinkman.Pinkman

class InputPinViewModel @ViewModelInject constructor(private val pinkman: Pinkman) : ViewModel() {

    val pinIsValid = MutableLiveData<Boolean>()

    fun validatePin(pin: String) {
        pinIsValid.value = pinkman.isValidPin(pin)
    }
}
