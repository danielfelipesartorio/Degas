package com.sartorio.degas.ui.orderdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.customcompoents.SearchableDialog
import com.sartorio.degas.ui.customcompoents.SearchableDialogClickListener
import com.sartorio.degas.ui.productdetails.ProductActivity
import kotlinx.android.synthetic.main.activity_order.*
import org.koin.android.viewmodel.ext.android.viewModel

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailsActivity : AppCompatActivity(), ProductListClickListener,
    SearchableDialogClickListener {

    private val orderDetailsViewModel: OrderDetailsViewModel by viewModel()

    private val productCodeDialog: SearchableDialog by lazy {
        val productCodesList =
            orderDetailsViewModel.getProductList().map { it.code }.toMutableList()
        SearchableDialog(this, productCodesList, this)
    }

    private lateinit var companyName: String
    private var date: Long = 0L
    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        companyName = (intent.getStringExtra(COMPANY_NAME) ?: return)
        date = intent.getLongExtra(ORDER_DATE, 0)
        order = orderDetailsViewModel.getOrderByClient(companyName, Date(date))
        supportActionBar?.title =
            "${order.clientName} - ${SimpleDateFormat(SIMPLE_DATE_FORMAT).format(order.date)}"
        initView()
    }

    override fun onResume() {
        orderDetailsViewModel.initViewModel(companyName, Date(date))
        super.onResume()
    }

    private fun initView() {
        setupListeners()
        setupObservers()
        recyclerViewProductList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        orderDetailsViewModel.initViewModel(companyName, Date(date))
    }

    private fun setupObservers() {
        orderDetailsViewModel.productOrderList.observe(this, Observer { order ->
            setupAdapter(order)
            val format = NumberFormat.getCurrencyInstance()
            textViewOrderCost.text =
                format.format(order.sumByDouble { it.quantity.values.sum() * it.product.cost })
            textViewOrderAmount.text = order.sumBy { it.quantity.values.sum() }.toString()
        })
    }

    private fun setupAdapter(list: MutableList<ProductOrder>) {

        recyclerViewProductList.adapter =
            ProductAdapter(list, this)
    }

    private fun setupListeners() {
        buttonAddProduct.setOnClickListener {
            productCodeDialog.show()
        }
    }

    override fun onClick(productOrder: ProductOrder) {
        startActivity(ProductActivity.createIntent(this, productOrder.product.code, companyName, Date(date)))
    }

    override fun onLongPress(productOrder: ProductOrder) {
        orderDetailsViewModel.removeOrder(productOrder)
    }

    override fun onListItemClick(item: String) {
        startActivity(ProductActivity.createIntent(this, item, companyName, Date(date)))
        productCodeDialog.dismiss()
    }

    companion object {
        const val COMPANY_NAME = "COMPANY_NAME"
        const val ORDER_DATE = "ORDER_DATE"
        const val SIMPLE_DATE_FORMAT = "dd/MM/yyyy"

        @JvmStatic
        fun createIntent(context: Context, companyName: String, date: Date): Intent {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            intent.putExtra(COMPANY_NAME, companyName)
            intent.putExtra(ORDER_DATE, date.time)
            return intent
        }
    }
}
