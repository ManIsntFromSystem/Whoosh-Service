package com.quantumman.whooshservice.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.quantumman.whooshservice.R

class ScannerFragment: Fragment() {

    lateinit var mScannerView: IntentIntegrator
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scanner, container, false)
        initScaner()
        return view
    }

    fun initScaner() {

    }

    companion object {
        fun newInstance() =
            ScannerFragment()
    }
}