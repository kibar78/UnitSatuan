package com.example.unitsatuan.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.LoginResponse
import com.example.unitsatuan.data.model.LoginUser
import com.example.unitsatuan.data.remote.ApiConfig
import com.example.unitsatuan.data.sharedpreferences.SharedPreferences
import com.example.unitsatuan.databinding.ActivityLoginBinding
import com.example.unitsatuan.ui.main.MainActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            var isEmptyFields = false
            if (username.isEmpty()){
                isEmptyFields = true
                binding.edtUsername.error = "Masukkan Username Anda"
            }
            if (password.isEmpty()){
                isEmptyFields = true
                binding.edtPassword.error = "Masukkan Password Anda"
            }
            if (!isEmptyFields){
                loginUser(userLogin = LoginUser(username, password))
                showLoading(true)
            }
        }
        

    }

    private fun loginUser(userLogin: LoginUser) {
        val client = ApiConfig.getApiService().login(userLogin)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    if (responseBody.level != "Admin" || responseBody.level != "Pimpinan"){
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Log.i("LoginActivity", "onSuccess: ${response.isSuccessful}")

                        val sharedPreferences = this@LoginActivity.getSharedPreferences(
                            this@LoginActivity.getString(R.string.shared_preferences_name_login),
                            Context.MODE_PRIVATE
                        )
                        val editor = sharedPreferences.edit()
                        val gson = Gson()
                        editor.putString(
                            SharedPreferences.KEY_CURRENT_USER_LOGIN, gson.toJson(
                            responseBody))
                        editor.apply()
                        finish()
                    }
                    else{
                        showLoading(false)
                        Toast.makeText(this@LoginActivity,"Hanya Unit Satuan yang dapat menggunakan aplikasi ini", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    showLoading(false)
                    Toast.makeText(this@LoginActivity,"Username atau Password Salah", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity","onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}