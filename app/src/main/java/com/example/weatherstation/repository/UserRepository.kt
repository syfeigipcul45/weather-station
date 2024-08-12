package com.example.weatherstation.repository

import com.example.weatherstation.request.LoginRequest
import com.example.weatherstation.response.LoginResponse
import com.example.weatherstation.service.ApiService
import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest) : Response<LoginResponse>? {
        return ApiService.getApi()?.loginUser(loginRequest = loginRequest)
    }
}