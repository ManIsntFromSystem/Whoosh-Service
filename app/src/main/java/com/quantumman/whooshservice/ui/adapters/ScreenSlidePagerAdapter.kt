package com.quantumman.whooshservice.ui.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.quantumman.whooshservice.ui.fragments.history.HistoryFragment
import com.quantumman.whooshservice.ui.fragments.scanner.ScannerFragment
import com.quantumman.whooshservice.ui.fragments.settings.SettingsFragment
import com.quantumman.whooshservice.util.AppContract.NUM_PAGES

class ScreenSlidePagerAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> ScannerFragment.newInstance()
        1 -> HistoryFragment.newInstance()
        2 -> SettingsFragment.newInstance()
        else -> ScannerFragment.newInstance()
    }
}