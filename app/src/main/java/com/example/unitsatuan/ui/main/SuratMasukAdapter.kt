package com.example.unitsatuan.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unitsatuan.ui.detail.DetailSuratMasukActivity
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.data.sharedpreferences.SharedPreferences
import com.example.unitsatuan.databinding.ItemSuratBinding
import com.google.gson.Gson

class SuratMasukAdapter: RecyclerView.Adapter<SuratMasukAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemSuratBinding): RecyclerView.ViewHolder(binding.root)

    var listSuratMasuk: List<SuratMasukItem?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSuratBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listSuratMasuk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tgl_surat = listSuratMasuk[position]?.tglSurat
        val perihal = listSuratMasuk[position]?.perihal
        val diteruskanKepada = listSuratMasuk[position]?.diteruskanKepada

        holder.binding.tvTanggalSurat.text = tgl_surat
        holder.binding.tvPerihal.text = perihal
        holder.binding.tvDiteruskanKepada.text = diteruskanKepada

        holder.itemView.setOnClickListener{v->
            val sharedPreferences = v.context.getSharedPreferences(
                v.context.getString(R.string.shared_preferences_name_surat),
                Context.MODE_PRIVATE
            )
            val editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString(SharedPreferences.KEY_CURRENT_SURAT, gson.toJson(listSuratMasuk[position]))
            editor.apply()

            val goDetail = Intent(v.context, DetailSuratMasukActivity::class.java)
            v.context.startActivity(goDetail)
        }
    }


}