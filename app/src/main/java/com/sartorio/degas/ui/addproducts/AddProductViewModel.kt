package com.sartorio.degas.ui.addproducts

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import com.sartorio.degas.model.Product
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.InputStream

class AddProductViewModel : ViewModel() {

    var headerMap = mutableMapOf<Int, String>()

    fun processFile(assets: AssetManager, fileName: String) {
        try {
            val myInput: InputStream = assets.open(fileName)
            val myFileSystem = POIFSFileSystem(myInput)
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
                    val colors: List<Int> = listOf()
                    val sizeAndStock: MutableMap<String, Int> = mutableMapOf()
                    var cost = 0.0
                    while (cellIter.hasNext()) {
                        val myCell = cellIter.next() as HSSFCell
                        when (headerMap[myCell.columnIndex]) {
                            CODE -> {
                                code = myCell.toString()
                            }
                            COLORS -> {
                                mapColors(myCell)
                            }
                            COST -> {
                                cost = myCell.toString().toDouble()
                            }
                            else -> {
                                sizeAndStock.put(
                                    headerMap[myCell.columnIndex] ?: return,
                                    myCell.toString().toInt()
                                )
                            }
                        }
                    }
                    Product(code, colors, sizeAndStock, cost)
                }
            }
        } catch (e: Exception) {

        }
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