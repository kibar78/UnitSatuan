package com.example.unitsatuan.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unitsatuan.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val suratRepository: SuratRepository, private val application: Application):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(MainViewModel::class.java)){
             return MainViewModel(suratRepository, application) as T
         }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, application: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), application)
            }.also { instance = it }
    }
}