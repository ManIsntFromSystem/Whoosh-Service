package com.quantumman.whooshservice.presenters

import android.util.Log
import com.quantumman.whooshservice.App
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.data.DataManager
import com.quantumman.whooshservice.data.model.Result
import com.quantumman.whooshservice.data.model.api.MessageResponse
import com.quantumman.whooshservice.data.model.db.Message
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import com.quantumman.whooshservice.ui.views.ScanView
import com.quantumman.whooshservice.util.AppContract.DEFAULT_QR_URL
import com.quantumman.whooshservice.util.AppContract.PLACE_HOLDER_COMMENTS
import com.quantumman.whooshservice.util.toMessage
import com.quantumman.whooshservice.util.toStatusScooterDataItem
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class ScannerPresenter : MvpPresenter<ScanView>() {
    @Inject
    lateinit var manager: DataManager

    fun handleNewMessage(url: String) {
        if (url.isNotEmpty() && url.startsWith(DEFAULT_QR_URL).not()) {
            viewState.showError(R.string.unknown_url_error)
        } else if (url.isNotEmpty() && url.startsWith(DEFAULT_QR_URL)) {
            fetchScooterStatus(url.takeLast(4))
        } else viewState.resumeCameraPreview()
    }

    private fun fetchScooterStatus(scooterName: String) {
        val apiKey = manager.getPreferencesRepository().getPrefApiKey()
        Log.d(TAG, "ApiKey: $apiKey")
        if (apiKey.isNullOrEmpty()) {
            viewState.showError(R.string.valid_api_key_error)
        } else {
//            val mess = manager.getApiRepository().getMessage(code = scooterName, apiKey = apiKey)
            val apiMessage = MessageResponse("Status", PLACE_HOLDER_COMMENTS)
            insertNewMessageToDB(apiMessage = apiMessage, scooter = scooterName)
        }
    }

    private fun insertNewMessageToDB(apiMessage: MessageResponse, scooter: String) {
        val date = Calendar.getInstance()
        val daoMessage = Message(
            date = date, name = scooter,
            status = apiMessage.status,
            comments = apiMessage.comments)
        Log.d("TAG", "DAO Message: $daoMessage")
        val idMessage = manager.getDbRepository().insertMessage(message = daoMessage)
        fetchMessageFromDb(idMessage)
    }

    private fun fetchMessageFromDb(id: Long) {
        when(val dbResponse = manager.getDbRepository().findById(id = id)) {
            is Result.Success<Message> -> {
                viewState.showResultScanned(dbResponse.data.toStatusScooterDataItem())
            }
            is Result.Error -> {
                viewState.showError(R.string.unknown_error)
                Log.d(TAG, "${dbResponse.statusCode} | ${dbResponse.message}")
            }
        }
    }

    fun resumePreview() = viewState.resumeCameraPreview()

    fun deleteScannedScooter(scooter: StatusScooterDataItem) {
        manager.getDbRepository().delete(scooter.toMessage())
    }

    init { App.graph.inject(this) }

    companion object {
        private val TAG = ScannerPresenter::class.simpleName
    }
}

//Examples
//            val currentDateString = DateFormat.getDateInstance().format(Date()).also { println(it) }
//            val currentTimeString = DateFormat.getTimeInstance().format(Date()).also { println(it) }
//            val date1 =  LocalDateTime.now().also { println(it) }
//            val date2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time).also { println(it) }