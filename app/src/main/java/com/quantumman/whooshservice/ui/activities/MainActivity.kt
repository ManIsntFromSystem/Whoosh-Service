package com.quantumman.whooshservice.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.data.local.pref.PreferencesRepository
import com.quantumman.whooshservice.ui.adapters.ScreenSlidePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var pagerCallback: ViewPager2.OnPageChangeCallback
    private lateinit var pref: PreferencesRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = PreferencesRepository(this)
        initViewPager()
        initViewPagerListener()
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId) {
                R.id.scan_menu -> 0
                R.id.history_menu -> 1
                R.id.settings_menu -> 2
                else -> 0
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initViewPager() {
        viewPager.adapter = ScreenSlidePagerAdapter(this)
    }

    private fun initViewPagerListener() {
        pagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigation.selectedItemId = when(position) {
                    0 -> R.id.scan_menu
                    1 -> R.id.history_menu
                    2 -> R.id.settings_menu
                    else -> R.id.scan_menu
                }
            }
        }
        viewPager.registerOnPageChangeCallback (pagerCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::pagerCallback.isInitialized) viewPager.unregisterOnPageChangeCallback(pagerCallback)
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
