package com.sartorio.degas.ui.productdetails

interface ProductClickListener {
    fun plusOne( color: Int, size: String)
    fun lessOne( color: Int, size: String)
}
