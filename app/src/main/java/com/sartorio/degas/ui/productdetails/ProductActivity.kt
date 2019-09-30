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
import java.util.*

class ProductActivity : AppCompatActivity(), ProductClickListener {

    override fun plusOne(color: Int, size: String) {
        productViewModel.plusOne(color, size)
    }

    override fun lessOne(color: Int, size: String) {
        productViewModel.lessOne(color, size)
    }

    private val productViewModel: ProductViewModel by viewModel()

    private lateinit var productCode: String
    private lateinit var companyName: String
    private var date: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productCode = intent.getStringExtra(PRODUCT) ?: return
        companyName = (intent.getStringExtra(COMPANY_NAME) ?: return)
        date = intent.getLongExtra(ORDER_DATE, 0)
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
        productViewModel.initViewModel(productCode,companyName, Date(date))
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
        const val COMPANY_NAME = "COMPANY_NAME"
        const val ORDER_DATE = "ORDER_DATE"

        @JvmStatic
        fun createIntent(
            context: Context,
            product: String,
            companyName: String,
            orderDate: Date
        ): Intent {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(PRODUCT, product)
            intent.putExtra(COMPANY_NAME,companyName)
            intent.putExtra(ORDER_DATE, orderDate.time)
            return intent
        }
    }
}
