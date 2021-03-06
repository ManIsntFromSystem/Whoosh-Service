package com.quantumman.whooshservice.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.quantumman.whooshservice.App
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.data.DataManager
import com.quantumman.whooshservice.util.checkValidApiKey
import com.quantumman.whooshservice.util.showSnack
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var manager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        btnCheckApiKey.setOnClickListener { checkValue() }
        checkPermission()

//        Handler(Looper.getMainLooper()).postDelayed({
//            checkPermission()
//        }, 300)
    }

    private fun checkPermission() {
        when {
            ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED -> requestPermission()
            manager.getPreferencesRepository().getPrefApiKey().isNullOrEmpty().not() -> goToMainActivity()
            else -> Log.d(TAG, "Unknown state")
        }
    }

    private fun requestPermission() = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraCodePermission)

    private fun checkValue() {
        val key = edTxtApiKey.text?.trim().toString()
        if (key.length < 15 || key.checkValidApiKey().not()){
            Toast.makeText(this, "Некоректнный ApiKey, попробуйте еще", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Incorrect ApiKey: $key")
        } else {
            manager.getPreferencesRepository().setPrefApiKey(key)
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraCodePermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) Log.d(TAG, "There is permission")
            else if (isUserPermanentDelayed()) { showDialogGoToSettings() }
            else requestPermission()
        }
    }

    private fun showDialogGoToSettings() = AlertDialog.Builder(this)
        .setTitle("Предоставьте Разрешения")
        .setMessage("Этому приложению нужен доступ к камере")
        .setPositiveButton("OK") { _, _ ->
            goToSettings() }
        .setNegativeButton("Отмена") { _, _ ->
            showSnack("Для запуска приложения, разрешите доступ к камере")
            finish()
        }.show()

    private fun goToSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivityForResult(intent, cameraCodePermission)
    }

    private fun isUserPermanentDelayed(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA).not()
        } else false
    }

    override fun onRestart() {
        super.onRestart()
        checkPermission()
    }

    init {
        App.graph.inject(this)
    }

    companion object {
        private const val cameraCodePermission = 111
        private val TAG = SplashActivity::class.simpleName
    }
}