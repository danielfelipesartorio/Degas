package com.sartorio.degas.ui.collections.productdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.R
import com.sartorio.degas.model.Product
import com.sartorio.degas.model.ProductOrder

class ProductOrderColorAdapter(
    private val product: Product,
    productOrder: List<ProductOrder>,
    private val listener: ProductClickListener
) :
    RecyclerView.Adapter<ProductOrderColorViewHolder>() {

    private var dataSet: List<ProductOrder> = productOrder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductOrderColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val linearLayout =
            inflater.inflate(R.layout.empty_linear_layout, null)
        return ProductOrderColorViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return product.colors.size
    }

    override fun onBindViewHolder(holder: ProductOrderColorViewHolder, position: Int) {
        val productOrder = dataSet.find { it.productColor == product.colors[position] } ?: return
        holder.format(product, productOrder, position, listener)
    }

}

