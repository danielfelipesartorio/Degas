package com.sartorio.degas.ui.orders.orderdetails

import com.sartorio.degas.model.ProductOrder

interface ProductListClickListener {
    fun onClick(productOrder: ProductOrder)
    fun onLongPress(productOrder: ProductOrder)
}