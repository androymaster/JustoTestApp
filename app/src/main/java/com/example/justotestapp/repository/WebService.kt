package com.example.justotestapp.repository

import com.example.justotestapp.application.AppConstants
import com.example.justotestapp.data.model.DataUser
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("api")
    suspend fun getUsers(@Query("results") results: Int): DataUser

    @GET("api")
    suspend fun getUsersFemale(
        @Query("results") results: Int,
        @Query("gender") gender: String
    ): DataUser
}

object RetrofitClient {
    val webservice: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}