package com.sartorio.degas.ui.addproducts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import com.sartorio.degas.R
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.InputStream


class AddProductsActivity : AppCompatActivity() {

    private val addProductViewModel: AddProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_products)
        selectFile()
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "*/*"
        }

        startActivityForResult(intent, OPEN_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            val inputStream = getInputStream(data?.data ?: return)
            addProductViewModel.processFile(inputStream)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getInputStream(uri: Uri): InputStream {
        return contentResolver.openInputStream(uri) ?: throw Exception()
    }

    private fun getFilePath(uri: Uri): String {
        return uri.path?.split(":")?.get(1) ?: throw Exception()
    }

    fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }


    companion object {
        const val OPEN_FILE_REQUEST = 101

        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, AddProductsActivity::class.java)
        }
    }


}
