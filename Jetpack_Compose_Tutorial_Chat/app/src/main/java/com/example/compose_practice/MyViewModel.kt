package com.example.compose_practice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * 2023-11-02
 * pureum
 */
class MyViewModel : ViewModel() {

    private var _myFlow = MutableStateFlow(0)
    val myFlow : StateFlow<Int> get() = _myFlow
    init {
        viewModelScope.launch {
            for (i in  1..100){
                _myFlow.value = i
                delay(500L)
            }
        }
    }

}