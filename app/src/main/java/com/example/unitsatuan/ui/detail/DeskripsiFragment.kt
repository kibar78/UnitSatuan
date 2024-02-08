package com.example.unitsatuan.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.data.sharedpreferences.SharedPreferences
import com.example.unitsatuan.databinding.FragmentDeskripsiBinding
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 * Use the [DeskripsiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeskripsiFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDeskripsiBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeskripsiBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences(
            getString(R.string.shared_preferences_name_surat),
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val suratMasuk = gson.fromJson(sharedPreferences?.getString(SharedPreferences.KEY_CURRENT_SURAT, ""), SuratMasukItem::class.java)


        suratMasuk.let { suratMasukItem ->
            binding?.tvKlasifikasi?.text = suratMasukItem.klasifikasi
            binding?.tvDerajat?.text = suratMasukItem.derajat
            binding?.tvNomorAgenda?.text = suratMasukItem.nomorAgenda
            binding?.tvDiterimaTgl?.text = suratMasukItem.tglPenerimaan
            binding?.tvTglSurat?.text = suratMasukItem.tglSurat
            binding?.tvSuratDari?.text = suratMasukItem.dariMana
            binding?.tvNomorSurat?.text = suratMasukItem.noSurat
            binding?.tvPerihal?.text = suratMasukItem.perihal
            binding?.tvIsiDisposisi?.text = suratMasukItem.isiDisposisi
            binding?.tvDiteruskanKepada?.text = suratMasukItem.diteruskanKepada
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeskripsiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeskripsiFragment()
    }
}