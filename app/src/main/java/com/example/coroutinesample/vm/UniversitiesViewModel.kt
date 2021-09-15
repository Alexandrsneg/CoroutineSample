package com.example.coroutinesample.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinesample.repository.University
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class UniversitiesViewModel : ViewModel() {
    private var cachedUniversities = arrayListOf<University>()
    var job: Job? = null
    val university: StateFlow<University>
        get() = _university

    val loadError: StateFlow<String>
        get() = _loadError

    val loading: StateFlow<Boolean>
        get() = _loading

    private val _university = MutableStateFlow(University(""))
    private val _loadError = MutableStateFlow("")
    private val _loading = MutableStateFlow(false)

    private val universitiesUseCase = UniversitiesUseCase()
    private val myExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable.localizedMessage ?: "Что-то пошло не так")
    }

    private fun onError(message: String) {
        _loading.value = false
        _loadError.value = message
    }

    fun getUniversities() {
        if (cachedUniversities.isNullOrEmpty()){
            _loading.value = true
            job = viewModelScope.launch(Dispatchers.IO + myExceptionHandler) {
                universitiesUseCase.getUniversities()
                universitiesUseCase.universityFlow?.collect {
                    cachedUniversities.add(it)
                    _university.value = it
                    _loading.value = false
                }
            }
        } else {
            _loading.value = false
            viewModelScope.launch(Dispatchers.Main) {
                cachedUniversities.forEach {
                    Log.e("TEST cached", it.name ?: "null")
                    _university.value = it
                }
            }
        }
    }


}