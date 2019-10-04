package com.sartorio.degas.ui.orderdetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sartorio.degas.model.ProductOrder
import kotlinx.android.synthetic.main.product_item.view.*

class ProductViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun format(product : ProductOrder){
        view.textViewProductCode.text = product.product.code
        view.textViewProductColor.text = String.format("%03d",product.productColor)
        view.textViewProductAmount.text = product.quantity.values.sum().toString()
    }
}