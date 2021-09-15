package com.example.coroutinesample.repository

import retrofit2.Response
import retrofit2.http.GET

interface UniversitiesApi {
    @GET("search?country=United+States")
    suspend fun getUniversities(): Response<List<University>>
}