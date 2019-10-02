package com.sartorio.degas.common

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sartorio.degas.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException




class PdfCreatorHelper(context: AppCompatActivity) {
    private val mContext: AppCompatActivity = context

    fun printPDF() {
        if (isExternalStorageWritable()) {
            val filename = getFileName()
            val file = File(getAlbumStorageDir("PDF"), filename)
            try {
                val outputStream = FileOutputStream(file)
                createPDF(outputStream)
                Toast.makeText(mContext, "SUCESSO", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(mContext, e.message, Toast.LENGTH_LONG).show()
            }

        }
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
    private fun getFileName(): String {
        //TODO: 06/10/2015
        return "file2" + ".pdf"
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


    private fun getContentView(): View {
        return mContext.findViewById(R.id.pdfLayout)
    }

    private fun getPrintAttributes(): PrintAttributes {
        val builder = PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setResolution(PrintAttributes.Resolution("res1", "Resolution", 50, 50))
            .setMinMargins(PrintAttributes.Margins(5, 5, 5, 5))
        return builder.build()
    }


    private fun getAlbumStorageDir(albumName: String): File {
        // Get the directory for the user's public pictures directory.
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            ), albumName
        )
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created")
        }
        return file
    }

    companion object {
        const val LOG_TAG = "Error"
    }
}