package com.example.m13demo.ui

import androidx.lifecycle.viewModelScope
import com.example.m13demo.data.User
import com.example.m13demo.communications.ServerRequests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
  * Holds the method to do "logout" from the server and fills the operation result,
 * which can be retrieved from the screen
 */
class MenuViewModel : IOViewModel() {

    fun logout() {
        viewModelScope.launch (Dispatchers.IO){
            var result: User?

            ServerRequests.logout()
         }
    }
}