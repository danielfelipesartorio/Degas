package com.sartorio.degas.ui.orderslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.R
import com.sartorio.degas.model.Order

class OrdersAdapter(
    private val orderList: List<Order>,
    private val listener: OrderListClickListener
) : RecyclerView.Adapter<OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(
            inflater.inflate(
                R.layout.order_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.format(orderList[position])
        holder.itemView.setOnClickListener {
            listener.onClick(orderList[position])
        }
        holder.itemView.setOnLongClickListener {
            listener.onLongPress(orderList[position])
            true
        }
    }

}

