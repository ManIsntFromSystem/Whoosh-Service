package com.quantumman.whooshservice.ui.fragments.scanner

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import com.quantumman.whooshservice.util.convertMessageToIntent
import kotlinx.android.synthetic.main.layout_scooter_result_dialog.*

class ScooterStatusDialog(var context: Context) : DialogStatus {

    private var idStatus: Int? = null
    private lateinit var dialog : Dialog
    private var statusScooter : StatusScooterDataItem? = null
    private var onDismissClickListener: OnDismissClickListener? = null
    private var onDeleteDialogClickListener: OnDeleteDialogClickListener? = null

    init {
        initDialog()
    }

    private fun initDialog() {
        dialog = Dialog(context).apply {
            setContentView(R.layout.layout_scooter_result_dialog)
            setCancelable(false)
        }
        clickListener()
    }

    fun showStatus(statusScooter: StatusScooterDataItem, id: Int? = null) {
        dialog.also {
            if (id != null) idStatus = id
            this.statusScooter = statusScooter
            it.tvNameScooter.text = statusScooter.name
            it.tvStatusScooter.text = statusScooter.status
            it.tvCommentsScooter.text = statusScooter.comments
            Log.d("TAG", "${statusScooter.date}")
            it.tvDateTimeScanned.text = statusScooter.date
            it.show()
        }
    }

    private fun clickListener() = dialog.also {
        it.btnCancel.setOnClickListener {
            onDismissClickListener?.onDismiss()
            dialog.dismiss()
        }
        it.btnDeleteStatus.setOnClickListener {
            statusScooter?.let { status ->
                onDeleteDialogClickListener?.onDelete(position = idStatus, scooter = status)
            }
            dialog.dismiss()
        }
        it.ivCopyStatus.setOnClickListener { copyResultToCache() }
        it.ivShareStatus.setOnClickListener { shareStatusScooter() }
    }

    fun setOnDismissClickListener(listener: OnDismissClickListener) {
        this.onDismissClickListener = listener
    }

    fun setOnDeleteClickListener(listenerDialog: OnDeleteDialogClickListener) {
        this.onDeleteDialogClickListener = listenerDialog
    }

    private fun shareStatusScooter() = context.startActivity(
        Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, statusScooter?.convertMessageToIntent())
    })


    private fun copyResultToCache() {
        val clipBird = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Scanned result", statusScooter?.convertMessageToIntent())
        clipBird.text = clip.getItemAt(0).text.toString()
        Toast.makeText(context, "Скопировано!", Toast.LENGTH_SHORT).show()
    }

    interface OnDismissClickListener {
        fun onDismiss()
    }
    interface OnDeleteDialogClickListener {
        fun onDelete(position: Int?, scooter: StatusScooterDataItem)
    }
}

