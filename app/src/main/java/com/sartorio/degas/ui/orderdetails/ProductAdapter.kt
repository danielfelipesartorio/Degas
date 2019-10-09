package com.sartorio.degas.ui.orderdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.R
import com.sartorio.degas.model.ProductOrder

class ProductAdapter(
    productList: MutableList<ProductOrder>,
    private val listener: ProductListClickListener
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val dataSet: MutableList<ProductOrder> = productList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(
            inflater.inflate(
                R.layout.product_item,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.format(dataSet[position])
        holder.itemView.setOnClickListener {
            listener.onClick(dataSet[position])
        }
        holder.itemView.setOnLongClickListener {
            listener.onLongPress(dataSet[position])
            true
        }
    }

}

