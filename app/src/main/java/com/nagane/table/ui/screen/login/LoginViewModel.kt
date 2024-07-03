package com.nagane.table.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Success : LoginUiState
    object Error : LoginUiState
    object Loding : LoginUiState
}

class LoginViewModel() : ViewModel() {
    var loginUiState : LoginUiState by mutableStateOf(LoginUiState.Loding)
        private set

    init {

    }

    private fun loginTable() {
        viewModelScope.launch {

        }
    }
}