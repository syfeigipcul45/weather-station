package com.example.weatherstation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherstation.databinding.ActivityMainBinding
import com.example.weatherstation.form.MenuActivity
import com.example.weatherstation.manager.SessionManager
import com.example.weatherstation.model.LoginViewModel
import com.example.weatherstation.response.BaseResponse
import com.example.weatherstation.response.LoginResponse

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            navigateToHome()
        }

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading(true)
                }

                is BaseResponse.Success -> {
                    showLoading(false)
                    processLogin(it.data)
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                is BaseResponse.Expired -> {
                    showLoading(false)
                    navigateToLogin()
                }

                else -> {
                    showLoading(false)
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun doLogin() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        viewModel.loginUser(username, password)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun processLogin(data: LoginResponse?) {
        showToast("Success: Login Berhasil")
        if (!data?.token.isNullOrEmpty()) {
            data?.token?.let { SessionManager.saveAuthToken(this, it) }
            navigateToHome()
        }
    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
