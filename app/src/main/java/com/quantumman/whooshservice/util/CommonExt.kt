package com.quantumman.whooshservice.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import java.text.SimpleDateFormat
import java.util.*


inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor: SharedPreferences.Editor = edit()
    action(editor)
    editor.apply()
}

fun StatusScooterDataItem?.convertMessageToIntent(): String = this?.let {
    "Самокат: ${it.name}\n Статус: ${it.status}\n " +
            "Комментарий: ${it.comments}\n Дата сканирования: ${it.date}"
} ?: ""

fun String.checkValidApiKey() = ("[^a-zA-Z0-9.]".toRegex()).containsMatchIn(this).not()

fun String.isSimpleCode() = ("[A-Z]+[0-9]{3}".toRegex()).matches(this.takeLast(4))

fun Calendar.fromCalendarToDisplay(): String = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(this.time)

fun String.fromStringToCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    calendar.time = sdf.parse(this)
    return calendar
}

fun Activity.showSnack(text: String) = Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()

fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, message, length)
    .setBackgroundTint(ContextCompat.getColor(this.context, R.color.colorPrimary))
    .show()

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(it) }
}

fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
fun main() {
    val code = "j456"
    if (code.isNotEmpty() && code.startsWith(AppContract.DEFAULT_QR_URL).not() && code.capitalize().isSimpleCode().not()) {
        println("Unknown")
    } else println("Well done")

    val api = "zJouBcMNMLaG5WhE6LyWMav1vMuFON896ucKSjIm"
    println(api.checkValidApiKey())
}