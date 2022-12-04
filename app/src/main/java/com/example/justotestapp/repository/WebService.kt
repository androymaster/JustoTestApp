package com.example.justotestapp.repository

import com.example.justotestapp.application.AppConstants
import com.example.justotestapp.data.model.ApiResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {
    @GET("?inc=name,location,picture")
    suspend fun getUser(): ApiResponse
}

object RetrofitClient {
    val webservice: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}