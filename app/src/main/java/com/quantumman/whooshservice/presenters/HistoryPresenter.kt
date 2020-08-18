package com.quantumman.whooshservice.presenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quantumman.whooshservice.App
import com.quantumman.whooshservice.data.DataManager
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import com.quantumman.whooshservice.ui.views.HistoryView
import com.quantumman.whooshservice.util.toMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class HistoryPresenter : MvpPresenter<HistoryView>() {
    @Inject
    lateinit var manager: DataManager

    private val _data = MutableLiveData<List<StatusScooterDataItem>>()
    val data: LiveData<List<StatusScooterDataItem>> = _data

    fun deleteStatus(status: StatusScooterDataItem, position: Int) {
        manager.getDbRepository().delete(status.toMessage())
        viewState.deleteById(position)
    }

    fun handleClickOnStatus(position: Int, item: StatusScooterDataItem) {
        viewState.showDialogItemStatus(position = position, statusScooter = item)
    }

    fun showNoData() {
        viewState.showNoItems()
    }

    init {
        GlobalScope.launch(Dispatchers.IO) {
            manager.getDbRepository().allMessages().collect { _data.postValue(it) }
        }
        App.graph.inject(this)
    }

    companion object {
        private val TAG = HistoryPresenter::class.simpleName
    }
}