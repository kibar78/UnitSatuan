package com.example.unitsatuan.data.model

import com.google.gson.annotations.SerializedName

data class LoginUser(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
