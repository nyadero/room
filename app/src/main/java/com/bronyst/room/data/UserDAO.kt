package com.bronyst.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun allUsers() : LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM users")
     suspend fun deleteAll()

//     search users
     @Query("SELECT * FROM users WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
     fun searchDatabase(searchQuery: String) :  Flow<List<User>>
}