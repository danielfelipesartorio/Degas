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
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


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
        val reader = PdfReader(mContext.resources.openRawResource(R.raw.degasform2021))
        val stamper = PdfStamper(reader, outputStream)
        val acroFields = stamper.acroFields

        var codes = ""
        var color = ""
        var amountPP = ""
        var amountP = ""
        var amountM = ""
        var amountG = ""
        var amountGG = ""
        var amountG1 = ""
        var price = ""


        acroFields.setField("companyName", order.client.name.companyName)
        acroFields.setField("fantasyName", order.client.name.fantasyName)
        acroFields.setField("clientCNPJ", order.client.documents.cnpj)
        acroFields.setField("clientSI", order.client.documents.stateRegistration)

        acroFields.setField("clientAddress", order.client.clientAddress.streetAddress)
        acroFields.setField("clientPostCode", order.client.clientAddress.postCode)
        acroFields.setField("clientDistrict", order.client.clientAddress.district)
        acroFields.setField("clientCity", order.client.clientAddress.city)
        acroFields.setField("clientState", order.client.clientAddress.state)

        acroFields.setField("contactName", order.client.contact.contactName)
        acroFields.setField("contatctEmail", order.client.contact.email)
        acroFields.setField("contactPhone1", order.client.contact.telephone)
        acroFields.setField("contactPhone2", order.client.contact.cellphone)

        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        acroFields.setField("obs", order.observations + "\nForma de Pagamento: " + order.paymentCondition + "\nPrevisão de entrega: " + SimpleDateFormat("dd/MM/yyyy").format(order.orderDate))
        acroFields.setField(
            "valueTotal",
            format.format(order.productList.sumByDouble { it.quantity.values.sum() * it.product.cost })
        )
        acroFields.setField(
            "amountTotal",
            order.productList.sumBy { it.quantity.values.sum() }.toString()
        )


        order.productList.forEach {
            codes += it.product.code + "\n"
            color += String.format("%03d", it.productColor) + "\n"
            amountPP += (it.quantity["PP"] ?: "-").toString() + "\n"
            amountP += (it.quantity["P"] ?: "-").toString() + "\n"
            amountM += (it.quantity["M"] ?: "-").toString() + "\n"
            amountG += (it.quantity["G"] ?: "-").toString() + "\n"
            amountGG += (it.quantity["GG"] ?: "-").toString() + "\n"
            amountG1 += (it.quantity["G1"] ?: "-").toString() + "\n"
            price += (format.format(it.product.cost).toString()) + "\n"
        }

        acroFields.setField("code", codes)
        acroFields.setField("color", color)
        acroFields.setField("amountPP", amountPP)
        acroFields.setField("amountP", amountP)
        acroFields.setField("amountM", amountM)
        acroFields.setField("amountG", amountG)
        acroFields.setField("amountGG", amountGG)
        acroFields.setField("amountG1", amountG1)
        acroFields.setField("price", price)

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

        startActivity(
            mContext,
            Intent.createChooser(intentShareFile, "Escolha uma das opções"),
            null
        )

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
        return "${order.client.name.companyName} - ${SimpleDateFormat("dd.MM.yyyy").format(order.orderDate)}" + ".pdf"
    }

}