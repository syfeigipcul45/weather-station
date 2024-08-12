package com.example.weatherstation.form.ui.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherstation.manager.SessionManager
import com.example.weatherstation.response.TelemetryResponse

class SensorViewModel : ViewModel() {
    private val _sensorTelemetry = MutableLiveData<TelemetryResponse>()
    val sensorTelemetry: LiveData<TelemetryResponse> = _sensorTelemetry

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "SensorViewModel"
//        private var token = SessionManager.getToken().toString()
    }
}