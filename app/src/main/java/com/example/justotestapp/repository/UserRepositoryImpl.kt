package com.example.justotestapp.repository

import com.example.justotestapp.data.model.DataUser
import com.example.justotestapp.data.remote.UserDataSources

class UserRepositoryImpl(private val dataSources: UserDataSources): UserRepository {

    override suspend fun getAllUser(): DataUser = dataSources.getAllUser()

    override suspend fun getAllUserFemale(): DataUser = dataSources.getAllUserFemale()
}