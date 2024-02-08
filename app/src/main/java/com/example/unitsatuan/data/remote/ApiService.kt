package com.example.unitsatuan.data.remote

import com.example.unitsatuan.data.model.LoginResponse
import com.example.unitsatuan.data.model.LoginUser
import com.example.unitsatuan.data.model.SuratMasukResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @Headers("ngrok-skip-browser-warning: 1234")
    @POST("SURAT/user/login_unit")
    fun login(@Body loginUser: LoginUser): Call<LoginResponse>

    @Headers("ngrok-skip-browser-warning: 123")
    @GET("SURAT/surat_masuk/search")
    suspend fun getPerihalMasuk(@Query("id")id: String): SuratMasukResponse

}