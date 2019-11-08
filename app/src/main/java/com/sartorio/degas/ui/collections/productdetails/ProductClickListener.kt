package com.sartorio.degas.ui.collections.productdetails

interface ProductClickListener {
    fun plusOne(color: Int, size: String)
    fun lessOne(color: Int, size: String)
}
