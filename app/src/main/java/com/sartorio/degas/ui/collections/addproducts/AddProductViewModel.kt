package com.sartorio.degas.ui.collections.addproducts

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Product
import com.sartorio.degas.repository.ProductRepository
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.InputStream

class AddProductViewModel constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val productsUpdatedCount = ObservableField<Int>()
    val newProductsCount = ObservableField<Int>()

    private var headerMap = mutableMapOf<Int, String>()

    private val tempProducts = mutableListOf<Product>()

    fun processFile(inputStream: InputStream) {
        try {
            val myFileSystem = POIFSFileSystem(inputStream)
            val myWorkBook = HSSFWorkbook(myFileSystem)
            val mySheet = myWorkBook.getSheetAt(0)

            val firstRow = mySheet.getRow(0)
            val firstRowIterator = firstRow.cellIterator()
            while (firstRowIterator.hasNext()) {
                val myCell = firstRowIterator.next() as HSSFCell
                headerMap[myCell.columnIndex] = myCell.toString()
            }

            val rowIter = mySheet.rowIterator()
            while (rowIter.hasNext()) {
                val myRow = rowIter.next() as HSSFRow
                if (myRow.rowNum != 0) {
                    val cellIter = myRow.cellIterator()
                    var code = ""
                    var colors: List<Int> = listOf()
                    val sizeAndStock: MutableMap<String, Int> = mutableMapOf()
                    var cost = 0.0
                    while (cellIter.hasNext()) {
                        val myCell = cellIter.next() as HSSFCell
                        when (headerMap[myCell.columnIndex]) {
                            CODE -> {
                                code = myCell.toString()
                            }
                            COLORS -> {
                                colors = mapColors(myCell)
                            }
                            COST -> {
                                cost = myCell.toString().toDouble()
                            }
                            else -> {
                                sizeAndStock.put(
                                    headerMap[myCell.columnIndex] ?: return,
                                    myCell.toString().split(".")[0].toInt()
                                )
                            }
                        }
                    }
                    if (code.isNotBlank() && colors.isNotEmpty() && cost != 0.0 && sizeAndStock.isNotEmpty()) {
                        tempProducts.add(Product(code, colors, sizeAndStock, cost))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TESTE", "" + e + e.message)
        }

        val currentProductsList = productRepository.getProductList()

        val codesInTheFile = tempProducts.map { it.code }
        val unchangedCodes: MutableList<Product> =
            currentProductsList.filter { it.code !in codesInTheFile }.toMutableList()

        productsUpdatedCount.set(currentProductsList.size - unchangedCodes.size)

        val newProductsList = mutableListOf<Product>()
        newProductsList.addAll(tempProducts)
        newProductsList.addAll(unchangedCodes)

        newProductsCount.set(newProductsList.size - currentProductsList.size)

        productRepository.updateProductList(newProductsList)
    }

    private fun mapColors(myCell: HSSFCell): List<Int> {
        return myCell.toString().splitToSequence(",").toList().map { it.toInt() }
    }

    companion object {
        const val CODE = "CODE"
        const val COLORS = "COLORS"
        const val COST = "COST"
    }
}