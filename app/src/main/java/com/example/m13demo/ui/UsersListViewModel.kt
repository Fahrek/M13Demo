package com.example.m13demo.ui

import androidx.lifecycle.viewModelScope
import com.example.m13demo.data.User
import com.example.m13demo.data.UsersListUiState
import com.example.m13demo.communications.ServerRequests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Fills the user list to retrieve from the screen
 * Also, holds the method to retrieve the data from the server
 */
class UsersListViewModel : IOViewModel() {


    private val _uiState = MutableStateFlow(UsersListUiState())
    val uiState: StateFlow<UsersListUiState> = _uiState.asStateFlow()

    fun setList(list: List<User>) {
        _uiState.update { currentState ->
            currentState.copy(
                list=list
            )
        }
    }

    fun getList():List<User>{
        return  uiState.value.list
    }

    /**
     * Call list on init so we can display status immediately.
     */
    init {
        list()
    }

    /** Retrieves from the server and holds the list of users */

    fun list() {

        viewModelScope.launch {

            var result: Array<User>?=null

            viewModelScope.launch (Dispatchers.IO){
                result = ServerRequests.listUsers()
                if (result == null) {
                    setGoodResult(false)
                    result = arrayOf()
                } else {
                    setGoodResult(true)
                }
                setList(result!!.toList())
            }



        }

    }
}
