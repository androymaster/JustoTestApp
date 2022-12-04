package com.example.justotestapp.repository

import com.example.justotestapp.data.model.ApiResponse
import com.example.justotestapp.data.model.User
import com.example.justotestapp.data.model.UserEntity

interface UserRepository {

    suspend fun getNewUser(): UserEntity
    suspend fun addUser(user: UserEntity)
    suspend fun deleteUser(toDelete: UserEntity)
    suspend fun getAllUser(): List<UserEntity>

    suspend fun getUserRemote(): ApiResponse
}