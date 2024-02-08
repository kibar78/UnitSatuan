package com.example.unitsatuan.repository

import com.example.unitsatuan.data.model.SuratMasukResponse
import com.example.unitsatuan.data.remote.ApiService

class SuratRepository private constructor(
    private val apiService: ApiService
){
    suspend fun getSuratMasuk(id: String): SuratMasukResponse {
        return apiService.getPerihalMasuk(id)
    }

    companion object{
        @Volatile
        private var instance: SuratRepository? = null
        fun getInstance(
            apiService: ApiService
        ): SuratRepository = instance?: synchronized(this){
            instance?: SuratRepository(apiService)
        }.also { instance = it }
    }
}