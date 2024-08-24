package com.example.weatherstation.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.weatherstation.repository.UserRepository
import com.example.weatherstation.request.LoginRequest
import com.example.weatherstation.response.BaseResponse
import com.example.weatherstation.response.LoginResponse
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.util.Date

class LoginViewModel(application: Application) : AndroidViewModel(application){
    val userRepository = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun loginUser(username: String, password: String) {
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username = username, password = password)
                val response = userRepository.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}