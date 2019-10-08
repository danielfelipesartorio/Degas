package com.sartorio.degas.ui.orderdetails

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.common.PdfCreatorHelper
import com.sartorio.degas.model.Order
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.customcompoents.SearchableDialog
import com.sartorio.degas.ui.customcompoents.SearchableDialogClickListener
import com.sartorio.degas.ui.productdetails.ProductActivity
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat

class OrderDetailsActivity : AppCompatActivity(), ProductListClickListener,
    SearchableDialogClickListener {

    private val orderDetailsViewModel: OrderDetailsViewModel by viewModel()
    private val dialog: AlertDialog by lazy {
            AlertDialog.Builder(this,R.style.TransparentDialog).apply {
                setView(R.layout.loading_dialog)
            }.create()
    }


    private val productCodeDialog: SearchableDialog by lazy {
        val productCodesList =
            orderDetailsViewModel.getProductList().map { it.code }.toMutableList()
        SearchableDialog(this, productCodesList, this)
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
            val context = this
            dialog.show()
            GlobalScope.launch {
                PdfCreatorHelper(context).printPDF(order)
                context.dialog.dismiss()
            }
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
            "${order.client.name.companyName} - ${SimpleDateFormat(SIMPLE_DATE_FORMAT).format(order.date)}"
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

    override fun onListItemClick(item: String) {
        startActivity(ProductActivity.createIntent(this, item, order.id))
        productCodeDialog.dismiss()
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
