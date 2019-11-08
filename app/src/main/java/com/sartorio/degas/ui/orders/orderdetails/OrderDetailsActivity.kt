package com.sartorio.degas.ui.orders.orderdetails

import SampleSearchModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.orders.exportorder.ExportOrderActivity
import com.sartorio.degas.ui.collections.productdetails.ProductActivity
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.activity_order.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat

class OrderDetailsActivity : AppCompatActivity(), ProductListClickListener{

    private val orderDetailsViewModel: OrderDetailsViewModel by viewModel()

    private val productCodeDialog: SimpleSearchDialogCompat<SampleSearchModel> by lazy {
        val productCodesList =
            orderDetailsViewModel.getProductList().map { it.code }.toMutableList()
        SimpleSearchDialogCompat(
            this,
            "Escolha a peça",
            "",
            null,
            ArrayList(productCodesList.map { SampleSearchModel(it) }),
            object : SearchResultListener<SampleSearchModel> {
                override fun onSelected(
                    dialog: BaseSearchDialogCompat<*>?,
                    item: SampleSearchModel?,
                    position: Int
                ) {
                    startActivity(ProductActivity.createIntent(this@OrderDetailsActivity, item?.title ?:return, order.id))
                    productCodeDialog.dismiss()
                }
            }
        )
    }

    private lateinit var order: Order
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val orderId = intent.getIntExtra(ORDER_ID, 0)
        order = orderDetailsViewModel.getOrderByClient(orderId)
        initView()
    }

    override fun onResume() {
        orderDetailsViewModel.initViewModel(order.id)

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.print) {
            startActivity(
                ExportOrderActivity.createIntent(this, order.id)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initView() {
        setupToolbar()
        setupListeners()
        setupObservers()
        recyclerViewProductList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        orderDetailsViewModel.initViewModel(order.id)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =
            "${order.client.name.companyName} - ${SimpleDateFormat(SIMPLE_DATE_FORMAT).format(order.orderDate)}"
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
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        } else {
            adapter = ProductAdapter(list, this)
            recyclerViewProductList.adapter = adapter
        }
    }

    private fun setupListeners() {
        buttonAddProduct.setOnClickListener {
            productCodeDialog.show()
        }
    }

    override fun onClick(productOrder: ProductOrder) {
        startActivity(ProductActivity.createIntent(this, productOrder.product.code, order.id))
    }

    override fun onLongPress(productOrder: ProductOrder) {
        orderDetailsViewModel.removeOrder(productOrder)
    }

    companion object {
        const val ORDER_ID = "ORDER_ID"
        const val SIMPLE_DATE_FORMAT = "dd/MM/yyyy"

        @JvmStatic
        fun createIntent(context: Context, orderId: Int): Intent {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            intent.putExtra(ORDER_ID, orderId)
            return intent
        }
    }
}
