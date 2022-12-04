package com.example.justotestapp.data.remote

import com.example.justotestapp.data.model.ApiResponse
import com.example.justotestapp.repository.WebService

class RemoteUserDataSources(private val webService: WebService) {

    suspend fun getUserName(): ApiResponse {
        try{
            return webService.getUser()
        }catch (e: Exception){ }
       return webService.getUser()
    }
}