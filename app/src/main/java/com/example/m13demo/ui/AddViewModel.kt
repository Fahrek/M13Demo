package com.example.m13demo.ui

import androidx.lifecycle.viewModelScope
import com.example.m13demo.communications.ServerRequests
import com.example.m13demo.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Holds the method to add a user to the server. Fills the operation result. This last data can be retrieved from the screen.
 */

class AddViewModel : IOViewModel() {

    /**
     * Adds a user to the server
     */
    fun add(username: String, password: String) {
        viewModelScope.launch (Dispatchers.IO){
            var result: Boolean

            result = ServerRequests.addUser(User(username, password))
            setGoodResult(result)
         }
    }
}