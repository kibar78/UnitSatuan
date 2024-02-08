package com.example.unitsatuan.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.unitsatuan.R
import com.example.unitsatuan.data.model.SuratMasukItem
import com.example.unitsatuan.databinding.ActivityDetailSuratMasukBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailSuratMasukActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailSuratMasukBinding

    private var suratMasuk : SuratMasukItem? = null

    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuratMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.image = suratMasuk
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}