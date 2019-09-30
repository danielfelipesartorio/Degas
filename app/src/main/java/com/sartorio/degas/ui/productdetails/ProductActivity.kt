package com.sartorio.degas.ui.productdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.ProductOrder
import kotlinx.android.synthetic.main.activity_product.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductActivity : AppCompatActivity(), ProductClickListener {
    private lateinit var productCode: String
    override fun plusOne(color: Int, size: String) {
        productViewModel.plusOne(color, size)
    }

    override fun lessOne(color: Int, size: String) {
        productViewModel.lessOne(color, size)
    }

    private val productViewModel: ProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productCode = intent.getStringExtra(PRODUCT) ?: return
        initView()
    }

    private fun initView() {
        setupListeners()
        setupObservers()
        recyclerViewProductOrderColorList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        productViewModel.initViewModel(productCode)
    }

    private fun setupObservers() {
        productViewModel.productOrders.observe(this, Observer {
            setupAdapter(it)
        })
    }

    private fun setupListeners() {
        buttonSaveProduct.setOnClickListener {
            productViewModel.saveAlterations()
            finish()
        }
    }

    private fun setupAdapter(productOrder: List<ProductOrder>) {
        recyclerViewProductOrderColorList.adapter =
            ProductOrderColorAdapter(productViewModel.getProduct(), productOrder, this)
    }

    companion object {
        const val PRODUCT = "PRODUCT"
    }
}
