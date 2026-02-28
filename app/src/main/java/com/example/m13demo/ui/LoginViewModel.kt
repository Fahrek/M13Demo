package com.example.m13demo.ui

import androidx.lifecycle.viewModelScope
import com.example.m13demo.communications.ServerRequests
import com.example.m13demo.data.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * Holds the method to log in to the server and fills the operation result, which can be retrieved from the screen.
 * Also, it fills if the operation has started to aid screen representation.
 */
class LoginViewModel : IOViewModel() {

     private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun setLoginTried(tried: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                loginTried=tried
            )
        }
    }

    /**
     * Logs in the server
     */
    fun doLogin(server:String, port:String, username: String, password: String) {
        viewModelScope.launch (Dispatchers.IO){
            var result: Boolean
            try {
                     result = ServerRequests.login(server, port.toInt(), username, password)
            } catch (nfe: NumberFormatException) {
                result = false
            }
            setGoodResult(result)
            setLoginTried(true)
        }
    }
}
