package com.quantumman.whooshservice.util

import android.content.SharedPreferences


inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor: SharedPreferences.Editor = edit()
    action(editor)
    editor.apply()
}

fun String.checkValidApiKey() = ("[^a-zA-Z0-9.]".toRegex()).containsMatchIn(this)