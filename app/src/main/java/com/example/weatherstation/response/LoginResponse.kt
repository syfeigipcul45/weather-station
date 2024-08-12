package com.example.weatherstation.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("scope")
	val scope: Any,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
