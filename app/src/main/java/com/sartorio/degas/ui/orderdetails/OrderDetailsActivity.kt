package com.sartorio.degas.ui.orderdetails

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import org.koin.android.viewmodel.ext.android.viewModel

import java.text.NumberFormat
import java.text.SimpleDateFormat

class OrderDetailsActivity : AppCompatActivity(), ProductListClickListener,
    SearchableDialogClickListener {

    private val orderDetailsViewModel: OrderDetailsViewModel by viewModel()

    private val productCodeDialog: SearchableDialog by lazy {
        val productCodesList =
            orderDetailsViewModel.getProductList().map { it.code }.toMutableList()
        SearchableDialog(this, productCodesList, this)
    }

    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        val orderId = intent.getIntExtra(ORDER_ID, 0)
        order = orderDetailsViewModel.getOrderByClient(orderId)
        supportActionBar?.title =
            "${order.client.name.companyName} - ${SimpleDateFormat(SIMPLE_DATE_FORMAT).format(order.date)}"
        initView()
    }

    override fun onResume() {
        orderDetailsViewModel.initViewModel(order.id)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.print){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        101)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                PdfCreatorHelper(this).printPDF()
            }
        }

        return super.onOptionsItemSelected(item)
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
        orderDetailsViewModel.initViewModel(order.id)
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
        fun createIntent(context: Context, orderId :Int): Intent {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            intent.putExtra(ORDER_ID, orderId)
            return intent
        }
    }
}
