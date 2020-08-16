package com.quantumman.whooshservice.data.local.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.quantumman.whooshservice.util.AppContract
import com.quantumman.whooshservice.util.AppContract.PREF_NAME
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

class PreferencesRepository(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun getPrefApiKey(): String? = pref.getString(AppContract.PREF_API_KEY, null)

    fun setPrefApiKey(apiKey: String): SharedPreferences.Editor? = pref.edit().apply {
        putString(AppContract.PREF_API_KEY, apiKey)
        apply()
    }
}