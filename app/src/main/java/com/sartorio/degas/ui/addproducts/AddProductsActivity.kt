package com.sartorio.degas.ui.addproducts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sartorio.degas.R
import org.koin.android.viewmodel.ext.android.viewModel


class AddProductsActivity : AppCompatActivity() {

    private val addProductViewModel: AddProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_products)
        selectFile()
    }

    private fun selectFile() {
        val fileName = "myexcelsheet.xls"
        addProductViewModel.processFile(assets, fileName)
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, AddProductsActivity::class.java)
        }
    }
}
