package com.quantumman.whooshservice.ui.fragments.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.presenters.SettingsPresenter
import com.quantumman.whooshservice.ui.views.SettingsView
import com.quantumman.whooshservice.util.snack
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class SettingsFragment : MvpAppCompatFragment(), SettingsView {

    @InjectPresenter
    lateinit var settingsPresenter: SettingsPresenter

    private lateinit var setView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setView = inflater.inflate(R.layout.fragment_settings, container, false)
        initListeners()
        return setView
    }

    private fun initListeners() {
        setView.btnChangeApiKey.setOnClickListener {
            settingsPresenter.changeApiKey(editChangeApiKey.text.toString().trim())
        }
        setView.btnClearAllStatus.setOnClickListener { showDialogDeleteAll() }
        setView.btnShowCurrentApiKey.setOnClickListener { settingsPresenter.showCurrentApiKey() }
    }

    private fun showDialogDeleteAll() = AlertDialog.Builder(context!!).apply {
        setTitle("УДАЛЕНИЕ ВСЕ ЗАПИСЕЙ")
        setMessage("Вы действительно хотите удалить все сканы?")
        setPositiveButton("Подтверждаю") {_, _ ->  settingsPresenter.deleteAllStatuses() }
        setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
    }.show()

    override fun changeApiKey() = setView.snack("Изменено!")

    override fun showError(emptyApiError: Int) = setView.snack(getString(emptyApiError))

    override fun showMessage(text: String) = setView.snack(text)

    companion object { fun newInstance() = SettingsFragment() }
}