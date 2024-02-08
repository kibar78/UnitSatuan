package com.example.unitsatuan.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.databinding.ActivityMainBinding
import com.example.unitsatuan.repository.Result
import com.example.unitsatuan.repository.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val suratAdapter = SuratMasukAdapter()

    private val mainViewModel by viewModels<MainViewModel>{
        ViewModelFactory.getInstance(applicationContext,application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.uiStateSuratMasuk.observe(this){uiStateSuratMasuk->
            when(uiStateSuratMasuk){
                is Result.Loading->{
                    showLoading(true)
                }
                is Result.Success->{
                    setSurat(uiStateSuratMasuk.data)
                    showLoading(false)
                }
                is Result.Error->{
                    Toast.makeText(this, uiStateSuratMasuk.error, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    override fun onResume() {
        mainViewModel.getSuratMasuk(MainViewModel.ID)
        super.onResume()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setSurat(dataSurat: List<SuratMasukItem?>){
        binding.rvSuratMasuk.layoutManager = LinearLayoutManager(this)
        suratAdapter.listSuratMasuk = dataSurat
        binding.rvSuratMasuk.adapter = suratAdapter
        binding.rvSuratMasuk.visibility = View.VISIBLE
        binding.rvSuratMasuk.setHasFixedSize(true)

        suratAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.pbLoading.visibility = View.VISIBLE
            binding.rvSuratMasuk.visibility = View.INVISIBLE
        }
        else {
            binding.pbLoading.visibility = View.GONE
            binding.rvSuratMasuk.visibility = View.VISIBLE
        }
    }
}