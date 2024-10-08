package com.example.weatherstation.form.ui.sensor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.weatherstation.R
import com.example.weatherstation.databinding.FragmentSensorBinding
import com.example.weatherstation.manager.SessionManager
import com.example.weatherstation.response.TelemetryResponse
import com.example.weatherstation.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SensorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentSensorBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "FragmentSensor"
//        private var token = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSensorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SensorViewModel.token =
            SessionManager.getToken(requireContext().applicationContext).toString()
        val sensorViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SensorViewModel::class.java)

        sensorViewModel.sensorTelemetry.observe(viewLifecycleOwner) { telemetryResponse ->
            getTelemetry(telemetryResponse)
        }

        sensorViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

    }

    private fun getTelemetry(response: TelemetryResponse) {
        binding.tvTemperature.text =
            response.temperature.get(0).value + " \u2103"
        binding.tvHumidity.text = response.humidity.get(0).value + " %"
        binding.tvCuaca.text = response.lightSensor.get(0).value + " lux"
        binding.tvRain.text = response.rainHour.get(0).value + " mm"
        binding.tvWindSpeed.text = response.windSpeed.get(0).value
        binding.tvWindDirection.text = response.windDirection.get(0).value
        binding.tvPm1.text = response.pM1_0.get(0).value
        binding.tvPm25.text = response.pM2_5.get(0).value
        binding.tvPm10.text = response.pM10.get(0).value
        getWindDirection(response.windDirection.get(0).value)
        getStatusPM1(response.pM1_0.get(0).value)
        getStatusPM2_5(response.pM2_5.get(0).value)
        getStatusPM10(response.pM10.get(0).value)
        getStatusCuaca(
            response.lightSensor.get(0).value,
            response.rainHour.get(0).value
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarSensor.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getWindDirection(value: String) {
        if (value.toDouble() >= 0 && value.toDouble() <= 33.7) {
            binding.tvValueDirection.text = "Utara"
        } else if (value.toDouble() >= 33.8 && value.toDouble() <= 78.7) {
            binding.tvValueDirection.text = "Timur Laut"
        } else if (value.toDouble() >= 78.8 && value.toDouble() <= 123.7) {
            binding.tvValueDirection.text = "Timur"
        } else if (value.toDouble() >= 123.8 && value.toDouble() <= 168.7) {
            binding.tvValueDirection.text = "Tenggara"
        } else if (value.toDouble() >= 168.8 && value.toDouble() <= 213.7) {
            binding.tvValueDirection.text = "Selatan"
        } else if (value.toDouble() >= 213.8 && value.toDouble() <= 258.7) {
            binding.tvValueDirection.text = "Barat Daya"
        } else if (value.toDouble() >= 258.8 && value.toDouble() <= 303.7) {
            binding.tvValueDirection.text = "Barat"
        } else if (value.toDouble() >= 303.8 && value.toDouble() <= 348.7) {
            binding.tvValueDirection.text = "Barat Laut"
        } else if (value.toDouble() >= 348.8 && value.toDouble() <= 360) {
            binding.tvValueDirection.text = "Utara"
        }
    }

    private fun getStatusCuaca(light: String, rain: String) {
        if ((light.toDouble() >= 0 && light.toDouble() <= 50) && (rain.toDouble() == 0.0)) {
            binding.imgLight.setImageResource(R.drawable.malam)
        } else if ((light.toDouble() >= 51 && light.toDouble() <= 500) && (rain.toDouble() == 0.0)) {
            binding.imgLight.setImageResource(R.drawable.mendung)
        } else if ((light.toDouble() >= 51 && light.toDouble() <= 500) && (rain.toDouble() >= 0.1 && rain.toDouble() <= 2.5)) {
            binding.imgLight.setImageResource(R.drawable.hujan)
        } else if ((light.toDouble() >= 51 && light.toDouble() <= 500) && (rain.toDouble() >= 2.6 && rain.toDouble() <= 7.5)) {
            binding.imgLight.setImageResource(R.drawable.hujan)
        } else if ((light.toDouble() >= 51 && light.toDouble() <= 500) && (rain.toDouble() >= 7.6 && rain.toDouble() <= 50.0)) {
            binding.imgLight.setImageResource(R.drawable.hujan_lebat)
        } else if ((light.toDouble() >= 51 && light.toDouble() <= 500) && (rain.toDouble() >= 50.1 && rain.toDouble() <= 100.0)) {
            binding.imgLight.setImageResource(R.drawable.hujan_lebat)
        } else if ((light.toDouble() >= 51 && light.toDouble() <= 500) && (rain.toDouble() >= 100.1)) {
            binding.imgLight.setImageResource(R.drawable.hujan_lebat)
        } else if ((light.toDouble() >= 1001 && light.toDouble() <= 25000) && (rain.toDouble() == 0.0)) {
            binding.imgLight.setImageResource(R.drawable.cerah_berawan)
        } else if ((light.toDouble() >= 1001 && light.toDouble() <= 25000) && (rain.toDouble() >= 0.1 && rain.toDouble() <= 2.5)) {
            binding.imgLight.setImageResource(R.drawable.hujan)
        } else if ((light.toDouble() >= 1001 && light.toDouble() <= 25000) && (rain.toDouble() >= 2.6 && rain.toDouble() <= 7.5)) {
            binding.imgLight.setImageResource(R.drawable.hujan)
        } else if ((light.toDouble() >= 1001 && light.toDouble() <= 25000) && (rain.toDouble() >= 7.6 && rain.toDouble() <= 50.0)) {
            binding.imgLight.setImageResource(R.drawable.hujan_lebat)
        } else if ((light.toDouble() >= 1001 && light.toDouble() <= 25000) && (rain.toDouble() >= 50.1 && rain.toDouble() <= 100.0)) {
            binding.imgLight.setImageResource(R.drawable.hujan_lebat)
        } else if ((light.toDouble() >= 1001 && light.toDouble() <= 25000) && (rain.toDouble() >= 100.1)) {
            binding.imgLight.setImageResource(R.drawable.hujan_lebat)
        } else if (light.toDouble() >= 25001 && light.toDouble() <= 100000) {
            binding.imgLight.setImageResource(R.drawable.cerah)
        } else if (light.toDouble() > 100000) {
            binding.imgLight.setImageResource(R.drawable.cerah)
        }
    }

    private fun getStatusPM1(value: String) {
        if (value.toInt() >= 0 && value.toInt() <= 10) {
            binding.imgStatusPM1.setImageResource(R.drawable.baik)
        } else if (value.toInt() >= 11 && value.toInt() <= 20) {
            binding.imgStatusPM1.setImageResource(R.drawable.sedang)
        } else if (value.toInt() >= 21 && value.toInt() <= 35) {
            binding.imgStatusPM1.setImageResource(R.drawable.tidak_sehat)
        } else {
            binding.imgStatusPM1.setImageResource(R.drawable.sangat_tidak_sehat)
        }
    }

    private fun getStatusPM2_5(value: String) {
        if (value.toDouble() >= 0 && value.toDouble() <= 15.5) {
            binding.imgStatusPM25.setImageResource(R.drawable.baik)
        } else if (value.toDouble() >= 15.6 && value.toDouble() <= 55.4) {
            binding.imgStatusPM25.setImageResource(R.drawable.sedang)
        } else if (value.toDouble() >= 55.5 && value.toDouble() <= 150.4) {
            binding.imgStatusPM25.setImageResource(R.drawable.tidak_sehat)
        } else if (value.toDouble() >= 150.5 && value.toDouble() <= 250.4) {
            binding.imgStatusPM25.setImageResource(R.drawable.sangat_tidak_sehat)
        } else {
            binding.imgStatusPM25.setImageResource(R.drawable.berbahaya)
        }
    }

    private fun getStatusPM10(value: String) {
        if (value.toInt() >= 0 && value.toInt() <= 50) {
            binding.imgStatusPM10.setImageResource(R.drawable.baik)
        } else if (value.toInt() >= 51 && value.toInt() <= 150) {
            binding.imgStatusPM10.setImageResource(R.drawable.sedang)
        } else if (value.toInt() >= 151 && value.toInt() <= 350) {
            binding.imgStatusPM10.setImageResource(R.drawable.tidak_sehat)
        } else if (value.toInt() >= 351 && value.toInt() <= 420) {
            binding.imgStatusPM10.setImageResource(R.drawable.sangat_tidak_sehat)
        } else {
            binding.imgStatusPM10.setImageResource(R.drawable.berbahaya)
        }
    }
}