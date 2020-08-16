package com.quantumman.whooshservice.util

import com.quantumman.whooshservice.BuildConfig

object AppContract {
    const val DEFAULT_QR_URL = "https://whoosh.app.link/scooter"
    const val DEFAULT_URL = "https://api.whoosh.bike/challenge/getinfo?code=P364"

    const val PREF_NAME = BuildConfig.APPLICATION_ID + "_pref"
    const val PREF_API_KEY = "api_key"

    const val DB_NAME = BuildConfig.APPLICATION_ID + ".db"
    const val TABLE_MESSAGE_NAME = "message"

    const val VIEW_TYPE_EMPTY = 0
    const val VIEW_TYPE_NORMAL = 1
}