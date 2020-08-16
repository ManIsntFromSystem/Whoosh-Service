package com.quantumman.whooshservice.data

import com.quantumman.whooshservice.data.local.db.DbRepository
import com.quantumman.whooshservice.data.local.pref.PreferencesRepository
import com.quantumman.whooshservice.data.remote.ApiRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dbRepository: DbRepository,
    private val preferencesRepository: PreferencesRepository
) {
    fun getApiRepository(): ApiRepository {
        return apiRepository
    }

    fun getDbRepository(): DbRepository {
        return dbRepository
    }

    fun getPreferencesRepository(): PreferencesRepository {
        return preferencesRepository
    }
}