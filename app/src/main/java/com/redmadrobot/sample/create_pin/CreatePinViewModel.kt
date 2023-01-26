package com.redmadrobot.sample.create_pin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.redmadrobot.pinkman.Pinkman
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePinViewModel @Inject constructor(private val pinkman: Pinkman) : ViewModel() {

    val pinIsCreated = MutableLiveData<Boolean>()

    fun createPin(pin: String) {
        pinkman.createPin(pin)

        pinIsCreated.postValue(true)
    }
}
