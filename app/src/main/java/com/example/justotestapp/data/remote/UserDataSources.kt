package com.example.justotestapp.data.remote

import com.example.justotestapp.application.AppConstants
import com.example.justotestapp.data.model.DataUser
import com.example.justotestapp.repository.WebService

class UserDataSources(private val webService: WebService) {

    suspend fun getAllUser(): DataUser {
        try{
            return webService.getUsers(AppConstants.RESULTS)
        }catch (e: Exception){ }
       return webService.getUsers(AppConstants.RESULTS)
    }

    suspend fun getAllUserFemale(): DataUser {
        try {
            return webService.getUsersFemale(AppConstants.RESULTS, AppConstants.GENDER)
        }catch (e: Exception){ }
        return webService.getUsersFemale(AppConstants.RESULTS, AppConstants.GENDER)
    }
}