package com.sartorio.degas.common

import android.content.Intent
import android.os.Environment
import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.itextpdf.text.Document
import com.itextpdf.text.List
import com.itextpdf.text.ListItem
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.sartorio.degas.R
import com.sartorio.degas.model.Order
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*


class PdfCreatorHelper(context: AppCompatActivity) {
    private val mContext: AppCompatActivity = context

    fun printPDF(order: Order) {
        if (isExternalStorageWritable()) {
            val filename = getFileName(order)
            val file = File(mContext.cacheDir, filename)
            try {
                val outputStream = FileOutputStream(file)
                createPDF(outputStream)
                Toast.makeText(mContext, getFileName(order), Toast.LENGTH_SHORT).show()
                shareFile(file)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
        savePdf(order)
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

    /**
     * Creates a PDF document and writes it to external storage using the
     * received FileOutputStream object
     * @param outputStream a FileOutputStream object
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun createPDF(outputStream: FileOutputStream) {
        val document = PrintedPdfDocument(
            mContext,
            getPrintAttributes()
        )

        // start a page
        val page = document.startPage(1)

        // draw something on the page
        val content = getContentView()
        content.draw(page.canvas)

        // finish the page
        document.finishPage(page)
        //. . .
        // add more pages
        //. . .
        // write the document content
        document.writeTo(outputStream)

        //close the document
        document.close()
    }

    private fun savePdf(order: Order) {
        //create object of Document class
        val mDoc = Document()
        //pdf file name
        val mFileName = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        //pdf file path
        val mFilePath =
            Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"

        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            //open the document for writing
            mDoc.open()
            //get text from EditText i.e. mTextEt


            //add author of the document (optional)
            mDoc.addAuthor("Atif Pervaiz")

            //add paragraph to the document
            mDoc.add(Paragraph(order.client.name.companyName))
            mDoc.add(Paragraph(order.client.name.fantasyName))
            mDoc.add(Paragraph(order.client.documents.cnpj))
            mDoc.add(Paragraph(order.client.documents.stateRegistration))

            val list = List(false)
            var stringSizes = "                      "
            for (size in order.productList.first().product.sizes) {
                stringSizes += size + "    "
            }
            list.add(stringSizes)
            order.productList.forEach { productOrder ->
                var string = ""
                productOrder.product.sizes.forEach {
                    string += (productOrder.quantity[it] ?: "0")
                    string += "    "
                }
                list.add(ListItem("" + productOrder.product.code + "    " + productOrder.productColor + "    " + string))
            }

            mDoc.add(list)


            //close the document
            mDoc.close()
            //show message that file is saved, it will show file name and file path too
            Toast.makeText(
                mContext,
                mFileName + ".pdf\nis saved to\n" + mFilePath,
                Toast.LENGTH_SHORT
            )
                .show()
        } catch (e: Exception) {
            //if any thing goes wrong causing exception, get and show exception message
            Toast.makeText(mContext, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getContentView(): View {
        return mContext.findViewById(R.id.pdfLayout)
    }

    private fun getPrintAttributes(): PrintAttributes {
        val builder = PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setResolution(PrintAttributes.Resolution("res1", "Resolution", 50, 50))
            .setMinMargins(PrintAttributes.Margins(5, 5, 5, 5))
        return builder.build()
    }
}