package com.bronyst.room.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDAO: UserDAO) {
    val getAllUsers : LiveData<List<User>> = userDAO.allUsers()

    suspend fun addUser(user: User){
        userDAO.insertUser(user)
    }

    suspend fun deleteUser(user: User){
        userDAO.deleteUser(user)
    }

    suspend fun deleteAll(){
        userDAO.deleteAll()
    }

    suspend fun updateUser(user: User){
        userDAO.updateUser(user)
    }

    fun searchDatabase(searchQuery: String): Flow<List<User>> {
        return userDAO.searchDatabase(searchQuery)
    }
}