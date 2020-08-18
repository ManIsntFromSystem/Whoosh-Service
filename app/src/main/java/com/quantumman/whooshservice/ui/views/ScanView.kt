package com.quantumman.whooshservice.ui.views

import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ScanView: MvpView {
    fun showError(error: Int)
    fun showResultScanned(statusScooter: StatusScooterDataItem)
    fun resumeCameraPreview()
}