package com.quantumman.whooshservice.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.data.DataManager
import com.quantumman.whooshservice.data.local.pref.PreferencesRepository
import com.quantumman.whooshservice.util.AppContract.DEFAULT_QR_URL
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var pref: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = PreferencesRepository(this)
        btnQrScan.setOnClickListener { initIntegrator() }
    }

    private fun initIntegrator() = IntentIntegrator(this@MainActivity).apply {
        captureActivity = CaptAct::class.java
        setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        setBarcodeImageEnabled(true)
        setOrientationLocked(true)
        setPrompt("Kick Scooter")
        setBeepEnabled(true)
        setCameraId(0)
        initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val integrator = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (integrator != null) {
            integrator.contents?.let { checkScannedQrValue(it) } ?: Log.d(TAG, "Cancelled")
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkScannedQrValue(qrValue: String) {
        if (qrValue.startsWith(DEFAULT_QR_URL)) {
            val name = qrValue.takeLast(4)
            val message = "Shared: ${pref.getPrefApiKey()}\n Name: $name"
            showResultScanned(message = message)
        } else {
            Toast.makeText(this, "Неизвестный URL", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Unknown Url: $qrValue")
        }
    }

    private fun showResultScanned(message: String) = AlertDialog.Builder(this)
        .setTitle("Scanned Result")
        .setMessage(message)
        .setPositiveButton("OK") { dialog, which ->
            println("Dialog: $which")
            dialog.cancel()
        }.show()

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
