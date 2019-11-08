package com.sartorio.degas.ui.orders.orderslist

import com.sartorio.degas.model.Order

interface OrderListClickListener {
    fun onClick(order: Order)
    fun onLongPress(order: Order)
}