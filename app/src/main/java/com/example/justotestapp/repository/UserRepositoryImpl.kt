package com.example.justotestapp.repository

import com.example.justotestapp.data.local.LocalUserDataSources
import com.example.justotestapp.data.model.ApiResponse
import com.example.justotestapp.data.model.UserEntity
import com.example.justotestapp.data.remote.RemoteUserDataSources

class UserRepositoryImpl(
    private val dataSourcesRemote: RemoteUserDataSources,
    private val dataSourcesLocal: LocalUserDataSources
) : UserRepository {

    //obtiene del Repo
    override suspend fun getUserRemote(): ApiResponse = dataSourcesRemote.getUserName()

    //Salva info en la BD
    override suspend fun getNewUser(): UserEntity {
        val myUserData = getUserRemote().results[0].name!!
        val myUserLocation = getUserRemote().results[0].location!!
        val myUserPicture = getUserRemote().results[0].picture

        val name = myUserData.first
        val lastname = myUserData.last
        val city = myUserLocation.city
        val state = myUserLocation.state
        val lat = myUserLocation.coordinates.latitude.toString()
        val lon = myUserLocation.coordinates.longitude.toString()
        val picture = myUserPicture?.thumbnail.toString()
        val user = UserEntity(name, lastname, state, city, picture, lat, lon, 0)
        return user
    }

    override suspend fun addUser(user: UserEntity) {
        dataSourcesLocal.saveUser(userEntity = user)
    }

    //delete one user
    override suspend fun deleteUser(toDelete: UserEntity) = dataSourcesLocal.deleteUser(toDelete)

    // get users BD
    override suspend fun getAllUser(): List<UserEntity> {
        return dataSourcesLocal.getAllUser()
    }
}