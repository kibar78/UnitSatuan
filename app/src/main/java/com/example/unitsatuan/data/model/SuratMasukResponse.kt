package com.example.unitsatuan.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SuratMasukResponse(

	@field:SerializedName("surat_masuk")
	val suratMasuk: List<SuratMasukItem?>? = null
)

@Parcelize
data class SuratMasukItem(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("diteruskan_kepada")
	val diteruskanKepada: String? = null,

	@field:SerializedName("tgl_penerimaan")
	val tglPenerimaan: String? = null,

	@field:SerializedName("image_surat")
	val imageSurat: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("perihal")
	val perihal: String? = null,

	@field:SerializedName("derajat")
	val derajat: String? = null,

	@field:SerializedName("isi_disposisi")
	val isiDisposisi: String? = null,

	@field:SerializedName("tgl_surat")
	val tglSurat: String? = null,

	@field:SerializedName("no_surat")
	val noSurat: String? = null,

	@field:SerializedName("lampiran")
	val lampiran: String? = null,

	@field:SerializedName("nomor_agenda")
	val nomorAgenda: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("klasifikasi")
	val klasifikasi: String? = null,

	@field:SerializedName("dari_mana")
	val dariMana: String? = null
): Parcelable
