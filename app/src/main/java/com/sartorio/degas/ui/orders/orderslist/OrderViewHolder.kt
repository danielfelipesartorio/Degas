package com.sartorio.degas.ui.orders.orderslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.model.Order
import kotlinx.android.synthetic.main.order_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class OrderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val dateFormat by lazy {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }

    fun format(order: Order) {
        view.textViewClientName.text = order.client.name.companyName
        view.textViewOrderDate.text = dateFormat.format(order.orderDate)
    }
}