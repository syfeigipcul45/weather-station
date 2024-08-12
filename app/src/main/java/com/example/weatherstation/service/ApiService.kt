package com.example.weatherstation.service

import com.example.weatherstation.request.LoginRequest
import com.example.weatherstation.response.LoginResponse
import com.example.weatherstation.response.TelemetryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @Headers(
        "Accept: application/vnd.github.v3.full+json",
        "Content-type: application/json"
    )
    @GET("plugins/telemetry/DEVICE/5689c170-404e-11ef-ab1e-91687af1eb47/values/timeseries/")
    fun getTelemetry(@Header("Authorization") token: String):Call<TelemetryResponse>

    companion object {
        fun getApi(): ApiService? {
            return ApiConfig.getServiceApi()
        }
    }
}