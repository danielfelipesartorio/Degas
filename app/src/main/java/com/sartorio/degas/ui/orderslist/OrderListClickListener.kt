package com.sartorio.degas.ui.orderslist

import com.sartorio.degas.model.Order

interface OrderListClickListener {
    fun onClick(order: Order)
    fun onLongPress(order: Order)
}