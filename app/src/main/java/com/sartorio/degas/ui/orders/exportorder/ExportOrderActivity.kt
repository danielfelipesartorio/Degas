package com.sartorio.degas.ui.orders.exportorder

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sartorio.degas.R
import com.sartorio.degas.common.PdfCreatorHelper
import com.sartorio.degas.databinding.ActivityExportOrderBinding
import com.sartorio.degas.model.Order
import kotlinx.android.synthetic.main.activity_export_order.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class ExportOrderActivity : AppCompatActivity() {

    private val dialog: AlertDialog by lazy {
        AlertDialog.Builder(this, R.style.TransparentDialog).apply {
            setView(R.layout.loading_dialog)
        }.create()
    }

    private val exportOrderViewModel: ExportOrderViewModel by viewModel()
    private lateinit var exportOrderActivityBinding: ActivityExportOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exportOrderActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_export_order)
        val orderId = intent.getIntExtra(ORDER_ID, 0)
        exportOrderViewModel.initViewModel(orderId)
        exportOrderActivityBinding.apply {
            this.exportOrderViewModel = this@ExportOrderActivity.exportOrderViewModel
        }
        init()
    }

    private fun init() {
        setupListeners()
    }

    private fun setupListeners() {
        buttonSend.setOnClickListener {
            exportOrderViewModel.order.observations = editTextObservations.text.toString()
            exportPdf()
        }

        editTextObservations.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                orderDetailsContainer.visibility = View.GONE
            } else {
                orderDetailsContainer.visibility = View.VISIBLE
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            exportOrderActivityBinding.editTextOrderDeliveryDate.setOnClickListener {
                val view = layoutInflater.inflate(R.layout.date_picker, null)
                val datePickerView: DatePicker = view.findViewById(R.id.datePicker)
                val dialog = AlertDialog.Builder(this)
                    .setView(view)
                    .create()
                datePickerView.setOnDateChangedListener { datePicker, year, month, day ->
                    exportOrderViewModel.setDeliveryDate(day, month, year)
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    private fun exportPdf() {
        val context = this
        dialog.show()
        GlobalScope.launch {
            PdfCreatorHelper(context).printPDF(exportOrderViewModel.order)
            context.dialog.dismiss()
        }
    }

    override fun onBackPressed() {
        if (editTextObservations.hasFocus()) {
            editTextObservations.clearFocus()
        } else {
            AlertDialog.Builder(this)
                .setMessage("Deseja salvar as alterções?")
                .setPositiveButton("Sim") { _, _ ->
                    exportOrderViewModel.order.paymentCondition =
                        exportOrderViewModel.paymentOptions.get() ?: exportOrderViewModel.order.paymentCondition
                    exportOrderViewModel.order.deliveryDate =
                        SimpleDateFormat("dd/MM/yyyy").parse(
                            exportOrderViewModel.deliveryDate.get() ?: ""
                        ) ?: exportOrderViewModel.order.deliveryDate
                    super.onBackPressed()
                }
                .setNegativeButton("Não") { _, _ -> super.onBackPressed() }
                .show()
        }
    }

    companion object {
        const val ORDER_ID = "ORDER_ID"
        @JvmStatic
        fun createIntent(context: Context, orderId: Int): Intent {
            return Intent(context, ExportOrderActivity::class.java).apply {
                putExtra(ORDER_ID, orderId)
            }
        }
    }
}
