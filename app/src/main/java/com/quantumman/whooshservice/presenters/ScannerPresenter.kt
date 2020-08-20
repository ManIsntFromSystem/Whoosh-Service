package com.quantumman.whooshservice.presenters

import android.content.Context
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
import com.quantumman.whooshservice.util.isConnectedToNetwork
import com.quantumman.whooshservice.util.isSimpleCode
import com.quantumman.whooshservice.util.toMessage
import com.quantumman.whooshservice.util.toStatusScooterDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class ScannerPresenter : MvpPresenter<ScanView>() {
    @Inject
    lateinit var manager: DataManager

    fun handleNewMessage(code: String, context: Context) = when {
        code.isNotEmpty() && code.startsWith(DEFAULT_QR_URL).not() && code.capitalize().isSimpleCode().not() -> {
            viewState.showError(R.string.unknown_qr_code)
            viewState.resumeCameraPreview()
        }
        code.isNotEmpty() && code.startsWith(DEFAULT_QR_URL) && code.takeLast(4).capitalize().isSimpleCode() ->  {
            fetchScooterStatus(code.takeLast(4).capitalize(), context)
        }
        code.isNotEmpty() && code.takeLast(4).capitalize().isSimpleCode() -> {
            fetchScooterStatus(code.takeLast(4).capitalize(), context)
        }
        else -> viewState.resumeCameraPreview()
    }

    private fun fetchScooterStatus(scooterName: String, context: Context) {
        if (context.isConnectedToNetwork().not()) {
            viewState.showError(R.string.connection_error)
            viewState.resumeCameraPreview()
            return
        }

//        Mock data for test
//        val mockData = MessageResponse(status = MOCK_MESSAGE_STATUS, comments = MOCK_MESSAGE_COMMENTS)
//        insertNewMessageToDB(apiMessage = mockData, scooter = scooterName)
//        return

        //Comments this piece of code for test with mockData ->
        val apiKey = manager.getPreferencesRepository().getPrefApiKey()
        if (apiKey.isNullOrEmpty()) {
            Log.d(TAG, "ApiKey: $apiKey")
            viewState.resumeCameraPreview()
            viewState.showError(R.string.valid_api_key_error)
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            when (val mess = manager.getApiRepository().getMessage(code = scooterName, apiKey = apiKey)) {
                is Result.Success -> insertNewMessageToDB(
                    apiMessage = mess.data,
                    scooter = scooterName
                )
                is Result.Error -> {
                    viewState.showError(error = "Ошибка загрузки")
                    viewState.resumeCameraPreview()
                    Log.d(TAG, "Status: ${mess.statusCode} \n Message: ${mess.message}")
                }
            }
        }
        // <-
    }

    private fun insertNewMessageToDB(apiMessage: MessageResponse, scooter: String) {
        val date = Calendar.getInstance()
        val daoMessage = Message(
            date = date, name = scooter,
            status = apiMessage.status,
            comments = apiMessage.comments)
        Log.d("TAG", "DAO Message: $daoMessage")
        try {
            val idMessage = manager.getDbRepository().insertMessage(message = daoMessage)
            fetchMessageFromDb(idMessage)
        } catch (e: Exception) { println(e) }
    }

    private fun fetchMessageFromDb(id: Long) {
        println("fetchMessageFromDb")
        try {
            GlobalScope.launch (Dispatchers.Main) {
                when (val dbResponse = manager.getDbRepository().findById(id = id)) {
                    is Result.Success<Message> -> {
                        viewState.showResultScanned(dbResponse.data.toStatusScooterDataItem())
                        viewState.resumeCameraPreview()
                    }
                    is Result.Error -> {
                        viewState.showError(R.string.unknown_error)
                        viewState.resumeCameraPreview()
                        Log.d(TAG, "${dbResponse.statusCode} | ${dbResponse.message}")
                    }
                }
            }
        } catch (e: Exception) { println(e) }
    }

    fun resumePreview() = viewState.resumeCameraPreview()

    fun deleteScannedScooter(scooter: StatusScooterDataItem) = manager.getDbRepository().delete(scooter.toMessage())

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