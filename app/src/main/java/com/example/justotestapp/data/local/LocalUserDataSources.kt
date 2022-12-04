package com.example.justotestapp.data.local

import androidx.lifecycle.LiveData
import com.example.justotestapp.data.model.UserEntity
import com.example.justotestapp.data.model.UserList
import com.example.justotestapp.data.model.toUsersList

class LocalUserDataSources(private val usersDao: UsersDao) {

    suspend fun getAllUser(): List<UserEntity> {
       return usersDao.getAll()
    }

    suspend fun saveUser(userEntity: UserEntity) {
        return usersDao.insert(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity){
        return usersDao.delete(userEntity)
    }
}