package com.example.unitsatuan.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.data.remote.ApiConfig
import com.example.unitsatuan.data.sharedpreferences.SharedPreferences
import com.example.unitsatuan.databinding.FragmentSuratBinding
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass.
 * Use the [SuratFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuratFragment : Fragment() {

    private var _binding: FragmentSuratBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuratBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences(
            getString(R.string.shared_preferences_name_surat),
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val suratMasuk = gson.fromJson(sharedPreferences?.getString(SharedPreferences.KEY_CURRENT_SURAT, ""), SuratMasukItem::class.java)

        suratMasuk.let { suratMasukItem ->
            val imageUrl = ApiConfig.BASE_URL + IMAGE_SURAT
            val image = imageUrl + "/" + suratMasuk.imageSurat
            Glide.with(requireActivity())
                .load(image)
                .into(binding?.ivSurat!!)
        }
    }

    companion object {
        const val IMAGE_SURAT = "/SURAT/assets/surat_masuk"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SuratFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SuratFragment()
    }
}