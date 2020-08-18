package com.quantumman.whooshservice.ui.fragments.scanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import com.quantumman.whooshservice.R
import com.quantumman.whooshservice.presenters.ScannerPresenter
import com.quantumman.whooshservice.ui.model.StatusScooterDataItem
import com.quantumman.whooshservice.ui.views.ScanView
import com.quantumman.whooshservice.util.snack
import kotlinx.android.synthetic.main.fragment_scanner.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class ScannerFragment : MvpAppCompatFragment(), ZXingScannerView.ResultHandler, ScanView {

    @InjectPresenter
    lateinit var scannerPresenter: ScannerPresenter

    private lateinit var scooterStatusDialog: ScooterStatusDialog
    private lateinit var zXingView: ZXingScannerView
    private lateinit var scannerView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = inflater.inflate(R.layout.fragment_scanner, container, false)
        flashListener()
        initDialog()
        initZXingScanner()
        return scannerView.rootView
    }

    private fun initZXingScanner() {
        try {
            zXingView = ZXingScannerView(context).apply {
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorTranslucent))
                setBorderColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                setLaserColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                setSquareViewFinder(true)
                setBorderStrokeWidth(10)
                setAutoFocus(true)
                setResultHandler(this@ScannerFragment)
            }
            scannerView.containerScanner.addView(zXingView)
            startZXingCamera()
        } catch (re: RuntimeException) {
            Log.d(TAG, "Unexpected exception while focusing", re)
        }
    }

    private fun flashListener() {
        scannerView.ivFlash.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                zXingView.flash = false
            } else {
                it.isSelected = true
                zXingView.flash = true
            }
        }
    }

    override fun resumeCameraPreview() = zXingView.resumeCameraPreview(this)

    override fun handleResult(rawResult: Result?) {
        rawResult?.text?.also {
            scannerPresenter.handleNewMessage(url = it)
        }
    }

    private fun startZXingCamera() = zXingView.startCamera()

    private fun initDialog() {
        scooterStatusDialog = ScooterStatusDialog(activity!!)
        scooterStatusDialog.setOnDismissClickListener(object :
            ScooterStatusDialog.OnDismissClickListener {
            override fun onDismiss() {
                scannerPresenter.resumePreview()
            }
        })
        scooterStatusDialog.setOnDeleteClickListener(object :
            ScooterStatusDialog.OnDeleteDialogClickListener {
            override fun onDelete(position: Int?, scooter: StatusScooterDataItem) {
                scannerPresenter.resumePreview()
                scannerPresenter.deleteScannedScooter(scooter)
            }
        })
    }

    override fun showResultScanned(statusScooter: StatusScooterDataItem) = scooterStatusDialog.showStatus(statusScooter)

    override fun onResume() {
        super.onResume()
        if (zXingView.isActivated.not()) zXingView.startCamera()
        zXingView.setResultHandler(this@ScannerFragment)
    }

    override fun onPause() {
        super.onPause()
        zXingView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        zXingView.stopCamera()
    }

    override fun showError(error: Int) = when(error) {
        R.string.connection_error -> scannerView.snack(getString(R.string.connection_error))
        R.string.unknown_url_error -> scannerView.snack(getString(R.string.unknown_url_error))
        R.string.valid_api_key_error -> scannerView.snack(getString(R.string.valid_api_key_error))
        else -> scannerView.snack(getString(R.string.unknown_error))
    }

    companion object {
        fun newInstance() = ScannerFragment()
        private val TAG = ScannerFragment::class.simpleName
    }
}

//AlertDialog.Builder(this.requireContext())
//.setTitle("Scanned Result")
//.setMessage("Scooter: ${message.name} \n Status: ${message.status} \n Comments: ${message.comments}")
//.setPositiveButton("OK") { dialog, _ ->
//    dialog.cancel()
//    resumeCameraPreview()
//}.show()