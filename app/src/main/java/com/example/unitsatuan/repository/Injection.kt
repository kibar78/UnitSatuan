package com.example.unitsatuan.repository

import android.content.Context
import com.example.unitsatuan.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): SuratRepository {
        val apiService = ApiConfig.getApiService()

        return SuratRepository.getInstance(apiService)
    }

}