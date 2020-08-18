package com.quantumman.whooshservice.ui.views

import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface HistoryView : MvpView{
    fun showStatuses(data: List<StatusScooterDataItem>)
    fun showError(error: Int)
    fun deleteById(idStatus: Int)
    fun deleteAllStatuses()
    fun retryDownLoad()
    fun showDialogItemStatus(position: Int, statusScooter: StatusScooterDataItem)
    fun showNoItems()
}