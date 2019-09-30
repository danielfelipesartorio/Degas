package com.sartorio.degas.ui.orderslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.model.Order
import kotlinx.android.synthetic.main.order_item.view.*

class OrderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun format(order: Order) {
        view.textViewClientName.text = order.clientName
        view.textViewOrderSize.text = order.productList.size.toString()
    }
}