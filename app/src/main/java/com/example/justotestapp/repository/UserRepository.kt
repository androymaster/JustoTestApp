package com.example.justotestapp.repository

import com.example.justotestapp.data.model.DataUser

interface UserRepository {
    suspend fun getAllUser() : DataUser
    suspend fun getAllUserFemale() : DataUser
}