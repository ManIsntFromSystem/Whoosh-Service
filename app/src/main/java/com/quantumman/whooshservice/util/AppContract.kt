package com.quantumman.whooshservice.util

import com.quantumman.whooshservice.BuildConfig

object AppContract {
    //Remote
    const val DEFAULT_QR_URL = "https://whoosh.app.link/scooter"
    const val DEFAULT_API_URL = "https://api.whoosh.bike/challenge/getinfo?code=P364"

    const val MOCK_MESSAGE_STATUS = "BATTERY LOW AND SOMETHING ELSE"
    const val MOCK_MESSAGE_COMMENTS = "Этот скутер в хорошем состоянии, кроме того, что он разряжен\n" +
            "Этот скутер в хорошем состоянии, кроме того, что он разряжен\n" +
            "Этот скутер в хорошем состоянии, кроме того, что он разряжен\n" +
            "Этот скутер в хорошем состоянии, кроме того, что он разряжен\n" +
            "Этот скутер в хорошем состоянии, кроме того, что он разряжен\n" +
            "Этот скутер в хорошем состоянии, кроме того, что он разряжен\n" +
            "Этот скутер в хорошем состоянии, кроме того, что он разряжен!!!"

    //SharedPref
    const val PREF_NAME = BuildConfig.APPLICATION_ID + "_pref"
    const val PREF_API_KEY = "api_key"

    //DB
    const val DB_NAME = BuildConfig.APPLICATION_ID + ".db"
    const val TABLE_MESSAGE_NAME = "message"

    //ViewPager count pages
    const val NUM_PAGES = 3
}