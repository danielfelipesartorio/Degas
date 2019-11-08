package com.sartorio.degas.ui.orders.orderslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.model.Order
import kotlinx.android.synthetic.main.order_item.view.*
import java.text.SimpleDateFormat

class OrderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun format(order: Order) {
        view.textViewClientName.text = order.client.name.companyName
        view.textViewOrderDate.text = SimpleDateFormat("dd/MM/yy").format(order.orderDate)
    }
}