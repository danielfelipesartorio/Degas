package com.sartorio.degas.ui.orderdetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.ProductOrder
import com.sartorio.degas.ui.customcompoents.SearchableDialog
import com.sartorio.degas.ui.customcompoents.SearchableDialogClickListener
import com.sartorio.degas.ui.productdetails.ProductActivity
import kotlinx.android.synthetic.main.activity_order.*
import org.koin.android.viewmodel.ext.android.viewModel

class OrderActivity : AppCompatActivity(), ProductListClickListener, SearchableDialogClickListener {

    private val orderViewModel: OrderViewModel by viewModel()

    private val productCodeDialog: SearchableDialog by lazy {
        val productCodesList = orderViewModel.getProductList().map { it.code }.toMutableList()
        SearchableDialog(this, productCodesList, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        initView()
    }

    override fun onResume() {
        orderViewModel.initViewModel()
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
        orderViewModel.initViewModel()
    }

    private fun setupObservers() {
        orderViewModel.orderList.observe(this, Observer {
            setupAdapter(it)
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
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ProductActivity.PRODUCT, productOrder.product.code)
        startActivity(intent)
    }

    override fun onLongPress(productOrder: ProductOrder) {
        orderViewModel.removeOrder(productOrder)
    }

    override fun onListItemClick(item: String) {
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(ProductActivity.PRODUCT, item)
        startActivity(intent)
        productCodeDialog.dismiss()
    }
}
