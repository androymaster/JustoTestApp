package com.example.justotestapp.presentation

import androidx.lifecycle.*
import com.example.justotestapp.core.Resource
import com.example.justotestapp.data.model.UserEntity
import com.example.justotestapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(private val repo: UserRepository): ViewModel() {

    fun getUsersRemote() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getUserRemote()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getNewUser() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getNewUser()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }


    fun getUserLocal() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getAllUser()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun addUser(user: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
           repo.addUser(user)
       }
    }

    fun deleteUser(toDelete: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteUser(toDelete)
        }
    }
}

class UserViewModelFactory(private val repo: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repo)
    }
}