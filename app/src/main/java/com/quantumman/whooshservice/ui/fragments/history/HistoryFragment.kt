package com.quantumman.whooshservice.ui.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.presenters.HistoryPresenter
import com.quantumman.whooshservice.ui.adapters.AdapterHistoryStatuses
import com.quantumman.whooshservice.ui.fragments.scanner.ScooterStatusDialog
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import com.quantumman.whooshservice.ui.views.HistoryView
import com.quantumman.whooshservice.util.snack
import kotlinx.android.synthetic.main.fragment_history.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class HistoryFragment: MvpAppCompatFragment(), HistoryView, AdapterHistoryStatuses.OnStatusListener, AdapterHistoryStatuses.OnDeleteClickListener {
    @InjectPresenter
    lateinit var historyPresenter: HistoryPresenter

    private lateinit var scooterStatusDialog: ScooterStatusDialog
    private lateinit var articleAdapter: AdapterHistoryStatuses

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        articleAdapter = AdapterHistoryStatuses(onDeleteItemListener = this, onStatusListener = this, items = arrayListOf())
        scooterStatusDialog = ScooterStatusDialog(context = requireActivity())
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initListenerDialog()
        historyPresenter.data.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty() || it == null ) historyPresenter.showNoData()
            else {
                articleAdapter.setData(it)
                recyclerHistory.visibility = View.VISIBLE
                layoutPlaceHolder.visibility = View.INVISIBLE
            }
        })
    }

    private fun initRecycler() = recyclerHistory.apply {
        layoutManager = LinearLayoutManager(activity)
        itemAnimator = DefaultItemAnimator()
        adapter = articleAdapter
    }

    private fun initListenerDialog() {
        scooterStatusDialog.setOnDeleteClickListener(object : ScooterStatusDialog.OnDeleteDialogClickListener {
            override fun onDelete(position: Int?, scooter: StatusScooterDataItem) {
                historyPresenter.deleteStatus(position = position!!, status = scooter)
            }
        })
    }

    override fun onStatusRecyclerClick(position: Int, itemStatus: StatusScooterDataItem) {
        historyPresenter.handleClickOnStatus(position, item = itemStatus)
    }

    override fun showNoItems() {
        recyclerHistory.visibility = View.INVISIBLE
        layoutPlaceHolder.visibility = View.VISIBLE
    }

    override fun showStatuses(data: List<StatusScooterDataItem>) {
        articleAdapter.setData(data)
    }

    override fun showError(error: Int) {
        requireView().snack(getString(error))
    }

    override fun deleteById(idStatus: Int) {
        articleAdapter.deleteStatus(id = idStatus)
    }

    override fun deleteAllStatuses() {
        articleAdapter.deleteAll()
    }

    override fun retryDownLoad() {

    }

    //Recycler
    override fun onDeleteItem(position: Int, itemStatus: StatusScooterDataItem) {
        historyPresenter.deleteStatus(position = position, status = itemStatus)
    }
    override fun showDialogItemStatus(position: Int, statusScooter: StatusScooterDataItem) {
        scooterStatusDialog.showStatus(statusScooter, id = position)
    }

    companion object {
        private val TAG = HistoryFragment::class.simpleName
        fun newInstance() = HistoryFragment()
    }
}