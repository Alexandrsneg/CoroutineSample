package com.example.coroutinesample.vm

import android.util.Log
import com.example.coroutinesample.repository.UniversitiesService
import com.example.coroutinesample.repository.University
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UniversitiesUseCase {
    private val universitiesService = UniversitiesService.getUniversitiesService()

    var universityFlow: Flow<University>? = null

    suspend fun getUniversities(){
        val universities = universitiesService.getUniversities()
        universityFlow = flow {
            universities.body()?.forEach {
                kotlinx.coroutines.delay(1000)
                Log.d("FLOW emit", it.name ?: "empty")
                emit(it)
            }
        }
    }
}