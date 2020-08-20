package com.quantumman.whooshservice.presenters

import com.quantumman.whooshservice.App
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.data.DataManager
import com.quantumman.whooshservice.ui.views.SettingsView
import com.quantumman.whooshservice.util.checkValidApiKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class SettingsPresenter : MvpPresenter<SettingsView>() {

    @Inject
    lateinit var manager: DataManager

    fun changeApiKey(text: String) {
        if (text.isNotEmpty() && text.length > 15 && text.checkValidApiKey()) {
            try {
                manager.getPreferencesRepository().setPrefApiKey(text.replace(" ", ""))
                viewState.changeApiKey()
            } catch (e: Exception) { println(e)}
        }
        else viewState.showError(R.string.empty_api_error)
    }

    fun deleteAllStatuses() {
        GlobalScope.launch {
            manager.getDbRepository().deleteAll()
            viewState.showMessage("Успешно удалено!")
        }
    }

    fun showCurrentApiKey() = try {
        val key = manager.getPreferencesRepository().getPrefApiKey()
        viewState.showMessage("Текущий ключ: $key")
    } catch (e: Exception) { println(e) }

    init {
        App.graph.inject(this)
    }
}