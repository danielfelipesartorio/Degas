package com.sartorio.degas.common

import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import com.sartorio.degas.R
import com.sartorio.degas.model.Order
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLConnection
import java.text.SimpleDateFormat


class PdfCreatorHelper(context: AppCompatActivity) {
    private val mContext: AppCompatActivity = context

    fun printPDF(order: Order) {
        if (isExternalStorageWritable()) {
            val filename = getFileName(order)
            val file = File(mContext.cacheDir, filename)
            try {
                fillFormPDF(order, FileOutputStream(file))
                shareFile(file)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun fillFormPDF(order: Order, outputStream: FileOutputStream) {
        val reader = PdfReader(mContext.resources.openRawResource(R.raw.blankform))
        val stamper = PdfStamper(reader, outputStream)
        val acroFields = stamper.acroFields

        var codes = ""
        var color = ""
        var amountPP = ""
        var amountP = ""
        var amountM = ""
        var amountG = ""
        var amountGG = ""


        acroFields.setField("companyName", order.client.name.companyName)
        acroFields.setField("fantasyName", order.client.name.fantasyName)
        acroFields.setField("contactName", order.client.contact.contactName)
        acroFields.setField("contatctEmail", order.client.contact.email)
        order.productList.forEach {
            codes += it.product.code + "\n"
            color += String.format("%03d", it.productColor) + "\n"
            amountPP += (it.quantity["PP"] ?: "0").toString() + "\n"
            amountP += (it.quantity["P"] ?: "0").toString() + "\n"
            amountM += (it.quantity["M"] ?: "0").toString() + "\n"
            amountG += (it.quantity["G"] ?: "0").toString() + "\n"
            amountGG += (it.quantity["GG"] ?: "0").toString() + "\n"
        }

        acroFields.setField("code", codes)
        acroFields.setField("color", color)
        acroFields.setField("amountPP", amountPP)
        acroFields.setField("amountP", amountP)
        acroFields.setField("amountM", amountM)
        acroFields.setField("amountG", amountG)
        acroFields.setField("amountGG", amountGG)

        stamper.close()
        reader.close()
    }

    private fun shareFile(file: File) {

        val intentShareFile = Intent(Intent.ACTION_SEND)
        val fileUri = FileProvider.getUriForFile(
            mContext,
            "com.sartorio.degas.provider",
            file
        )

        intentShareFile.type = URLConnection.guessContentTypeFromName(file.name)
        intentShareFile.putExtra(
            Intent.EXTRA_STREAM,
            fileUri
        )

        //if you need
        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject);
        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");

        startActivity(mContext, Intent.createChooser(intentShareFile, "Share File"), null)

    }

    /**
     * Checks if external storage is available for read and write
     * @return boolean
     */
    private fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    /**
     * Returns a name for the file that will be created
     * @return String
     */
    private fun getFileName(order: Order): String {
        return "${order.client.name.companyName} - ${SimpleDateFormat("dd.MM.yyyy").format(order.date)}" + ".pdf"
    }

}