package com.sartorio.degas.ui.orderdetails

import com.sartorio.degas.model.ProductOrder

interface ProductListClickListener {
    fun onClick(productOrder: ProductOrder)
    fun onLongPress(productOrder: ProductOrder)
}