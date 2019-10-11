package com.sartorio.degas.ui.productdetails

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.R
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder
import kotlinx.android.synthetic.main.color_label_item.view.*
import kotlinx.android.synthetic.main.empty_linear_layout.view.*
import kotlinx.android.synthetic.main.product_size_item.view.*

class ProductOrderColorViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun format(
        product: Product,
        productOrder: ProductOrder,
        position: Int,
        listener: ProductClickListener
    ) {
        view.layout.removeAllViews()
        val inflater = LayoutInflater.from(view.context)
        val colorLabel = inflater.inflate(R.layout.color_label_item, null)
        colorLabel.textViewColorLabel.text = String.format("%03d", product.colors[position])
        view.layout.addView(colorLabel)
        for (size in product.sizes) {
            val itemView = inflater.inflate(R.layout.product_size_item, null)
            view.layout.addView(itemView)
            itemView.textViewSizeLabel.text = size.key
            itemView.textViewAmountValue.text = (productOrder.quantity[size.key] ?: "0").toString()
            itemView.textViewStockValue.text = "Estoque: ${(product.sizes[size.key] ?: "0")}"
            itemView.imageButtonAdd.setOnClickListener {
                listener.plusOne(product.colors[position], size.key)
            }
            itemView.imageButtonRemove.setOnClickListener {
                listener.lessOne(product.colors[position], size.key)
            }
        }
    }
}

