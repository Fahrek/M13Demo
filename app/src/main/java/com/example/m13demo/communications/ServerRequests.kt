package com.example.m13demo.communications

import com.example.m13demo.data.User
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Object with functions for calling the server
 */

object ServerRequests {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()

    suspend fun login(serverName: String, port: Int, username:String, password:String):Boolean = accessMutex.withLock {
        CommController.setServerName(serverName)
        CommController.setPort(port)
        return (CommController.doLogin(username, password)==CommController.OK_RETURN_CODE)
    }

    suspend fun logout():Boolean = accessMutex.withLock {
        return (CommController.doLogout()==CommController.OK_RETURN_CODE)
    }

    suspend fun isLogged():Boolean = accessMutex.withLock {
        return CommController.isLogged
    }

    suspend fun queryUser(username:String): User? = accessMutex.withLock {
          return CommController.doQueryUser(username)
    }

    suspend fun listUsers():Array<User>? = accessMutex.withLock {
        return CommController.doListUsers()
    }

    suspend fun addUser(user:User):Boolean = accessMutex.withLock {
        return (CommController.doAddUser(user)==CommController.OK_RETURN_CODE)

    }

}

