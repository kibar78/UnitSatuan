package com.example.unitsatuan.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.LoginResponse
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.data.sharedpreferences.SharedPreferences
import com.example.unitsatuan.repository.Result
import com.example.unitsatuan.repository.SuratRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainViewModel(private val suratRepository: SuratRepository,
                    private val application: Application): ViewModel() {

    private val _uiStateSuratMasuk = MutableLiveData<Result<List<SuratMasukItem?>>>()
    val uiStateSuratMasuk: LiveData<Result<List<SuratMasukItem?>>> = _uiStateSuratMasuk

    private var userLogin: LoginResponse? = null

    companion object {
        val ID = ""
    }

    fun getSuratMasuk(id: String) {
        _uiStateSuratMasuk.value = Result.Loading
        viewModelScope.launch {
            try {
                val sharedPreferences = application.getSharedPreferences(
                    application.getString(R.string.shared_preferences_name_login),
                    Context.MODE_PRIVATE
                )
                val gson = Gson()
                userLogin = gson.fromJson(
                    sharedPreferences?.getString
                        (SharedPreferences.KEY_CURRENT_USER_LOGIN, ""),
                    LoginResponse::class.java)

                val filteredSuratMasuk = suratRepository.getSuratMasuk(id).suratMasuk?.filter {
                    Log.i("MainViewModel","${userLogin}, diteruskan_kepada: ${it?.diteruskanKepada}")
                    it?.diteruskanKepada?.contains(userLogin?.level!!) == true
                }
                _uiStateSuratMasuk.value = Result.Success(filteredSuratMasuk!!)
            } catch (e: Exception) {
                _uiStateSuratMasuk.value = Result.Error(e.message.toString())
            }
        }
    }
}