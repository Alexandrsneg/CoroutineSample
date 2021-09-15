package com.example.coroutinesample.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UniversitiesService {
    private val BASE_URL = "http://universities.hipolabs.com/"

    fun getUniversitiesService(): UniversitiesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversitiesApi::class.java)
    }
}