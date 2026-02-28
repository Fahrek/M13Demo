package com.example.m13demo.ui

import androidx.lifecycle.viewModelScope
import com.example.m13demo.data.QueryUiState
import com.example.m13demo.data.User
import com.example.m13demo.communications.ServerRequests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Fills the name of the user to retrieve from the screen
 * Also, holds the method to retrieve the data from the server
 */
class QueryViewModel : IOViewModel() {


    private val _uiState = MutableStateFlow(QueryUiState())
    val uiState: StateFlow<QueryUiState> = _uiState.asStateFlow()


    fun setName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name=name
            )
        }
    }

    fun getName():String{
        return  uiState.value.name;
    }

    /**
     * gets a user from the server
     */
    fun getUser(username: String) {

        var result: User?
        viewModelScope.launch (Dispatchers.IO){
            result = ServerRequests.queryUser(username)

            if(result==null){
                setGoodResult(false)
                setName("")
            }else{
                setGoodResult(true)
                setName(result!!.name)
            }

        }
    }

}