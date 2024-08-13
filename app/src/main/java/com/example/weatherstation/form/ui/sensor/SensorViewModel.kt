package com.example.weatherstation.form.ui.sensor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherstation.manager.SessionManager
import com.example.weatherstation.response.TelemetryResponse
import com.example.weatherstation.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SensorViewModel : ViewModel() {
    private val _sensorTelemetry = MutableLiveData<TelemetryResponse>()
    val sensorTelemetry: LiveData<TelemetryResponse> = _sensorTelemetry

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "SensorViewModel"
        public var token = ""
    }

    init {
        getTelemetry(token)
    }

    private fun getTelemetry(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getServiceApi().getTelemetry("Bearer "+token)
        client.enqueue(object : Callback<TelemetryResponse> {
            override fun onResponse(
                call: Call<TelemetryResponse>,
                response: Response<TelemetryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _sensorTelemetry.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TelemetryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}