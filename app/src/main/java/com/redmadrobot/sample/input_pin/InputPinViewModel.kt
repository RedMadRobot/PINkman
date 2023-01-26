package com.redmadrobot.sample.input_pin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redmadrobot.pinkman.Pinkman
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputPinViewModel @Inject constructor(private val pinkman: Pinkman) : ViewModel() {

    val pinIsValid = MutableLiveData<Boolean>()

    fun validatePin(pin: String) {
        pinIsValid.value = pinkman.isValidPin(pin)
    }
}
