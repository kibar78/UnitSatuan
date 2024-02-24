package com.example.unitsatuan.ui.detail

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.data.remote.ApiConfig
import com.example.unitsatuan.data.sharedpreferences.SharedPreferences
import com.example.unitsatuan.databinding.FragmentLampiranBinding
import com.google.gson.Gson
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [LampiranFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LampiranFragment : Fragment() {

    private var _binding: FragmentLampiranBinding? = null
    private val binding get() = _binding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLampiranBinding.inflate(inflater, container, false)
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

        val imageUrl = ApiConfig.BASE_URL + IMAGE_SURAT
        val image = imageUrl + "/" + suratMasuk.lampiran

        suratMasuk.let {
            Glide.with(requireActivity())
                .load(image)
                .into(binding?.ivLampiran!!)
        }
        binding?.btnImage?.setOnClickListener {
            Glide.with(requireActivity())
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        // Save the Bitmap to the gallery
                        saveImageToGallery(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle resource cleared
                    }
                })
        }

        binding?.btnPdf?.setOnClickListener {
            Glide.with(requireActivity())
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        // Save the Bitmap to the gallery
                        saveImageAsPdf(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle resource cleared
                    }
                })
        }
    }

    private fun saveImageAsPdf(bitmap: Bitmap?) {
        try {
            val document = Document()

            document.pageSize = Rectangle(bitmap!!.width.toFloat(), bitmap.height.toFloat())

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val pdfFileName = "Surat_masuk_$timestamp.pdf"

            val externalDocumentsDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val pdfFilePath = File(externalDocumentsDir, pdfFileName).absolutePath

            PdfWriter.getInstance(document, FileOutputStream(pdfFilePath))
            document.open()

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()

            // Convert ByteArray to iTextPDF Image
            val image = Image.getInstance(byteArray)
            image.scaleToFit(bitmap.width.toFloat(), bitmap.height.toFloat())

            // Calculate the X and Y positions to center the image
            val x = (document.pageSize.width - image.width) / 2
            val y = (document.pageSize.height - image.height) / 2
            image.setAbsolutePosition(x, y)
            document.add(image)

            document.close()

            Toast.makeText(requireContext(), "PDF saved to $pdfFilePath", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to save PDF", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap?) {
        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "Image_surat_masuk.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            if (uri != null) {
                requireActivity().contentResolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                    Toast.makeText(requireContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
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
         * @return A new instance of fragment LampiranFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LampiranFragment()
    }
}