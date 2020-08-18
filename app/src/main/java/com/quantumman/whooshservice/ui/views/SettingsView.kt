package com.quantumman.whooshservice.ui.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SettingsView : MvpView {
    fun changeApiKey()
    fun showError(emptyApiError: Int)
    fun showMessage(text: String)
}