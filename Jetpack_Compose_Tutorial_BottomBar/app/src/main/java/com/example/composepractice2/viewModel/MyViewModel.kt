package com.example.composepractice2.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 2023-10-30
 * pureum
 */
class MyViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(0)
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            (1..100).asFlow().collect{
                delay(1000L)
                _uiState.value = it
                Log.e("TAG", "viewModel : $it", )
            }
        }
    }
}