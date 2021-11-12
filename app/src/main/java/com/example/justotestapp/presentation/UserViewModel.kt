package com.example.justotestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.justotestapp.core.Resource
import com.example.justotestapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class UserViewModel(private val repo: UserRepository): ViewModel() {

    fun getUsers() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getAllUser()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getUsersFemale() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getAllUserFemale()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class UserViewModelFactory(private val repo: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repo)
    }
}