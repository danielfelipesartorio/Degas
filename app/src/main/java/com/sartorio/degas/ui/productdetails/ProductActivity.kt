package com.sartorio.degas.ui.productdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.ProductOrder
import kotlinx.android.synthetic.main.activity_product.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductActivity : AppCompatActivity(), ProductClickListener {

    override fun plusOne(color: Int, size: String) {
        productViewModel.plusOne(color, size)
    }

    override fun lessOne(color: Int, size: String) {
        productViewModel.lessOne(color, size)
    }

    private val productViewModel: ProductViewModel by viewModel()

    private lateinit var productCode: String
    private var orderId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productCode = intent.getStringExtra(PRODUCT) ?: return
        orderId = intent.getIntExtra(ORDER_ID, 0)

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
        productViewModel.initViewModel(productCode, orderId)
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
        const val ORDER_ID = "ORDER_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            product: String,
            orderId: Int
        ): Intent {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(PRODUCT, product)
            intent.putExtra(ORDER_ID, orderId)
            return intent
        }
    }
}
