package com.example.unitsatuan.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("no_hp")
	val noHp: Any? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("image_profile")
	val imageProfile: Any? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: Any? = null,

	@field:SerializedName("bidang_pekerjaan")
	val bidangPekerjaan: Any? = null,

	@field:SerializedName("username")
	val username: String? = null
)
