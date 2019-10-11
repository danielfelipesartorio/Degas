package com.sartorio.degas.ui.addproducts

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sartorio.degas.R
import com.sartorio.degas.databinding.ActivityAddProductsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.InputStream


class AddProductsActivity : AppCompatActivity() {

    private val addProductViewModel: AddProductViewModel by viewModel()

    private lateinit var activityAddProductsBinding: ActivityAddProductsBinding

    private val dialog: AlertDialog by lazy {
        AlertDialog.Builder(this, R.style.TransparentDialog).apply {
            setView(R.layout.loading_dialog)
        }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddProductsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_add_products)
        activityAddProductsBinding.addProductViewModel = addProductViewModel
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
            dialog.show()
            GlobalScope.launch {
                addProductViewModel.processFile(inputStream)
                dialog.dismiss()
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getInputStream(uri: Uri): InputStream {
        return contentResolver.openInputStream(uri) ?: throw Exception()
    }



    companion object {
        const val OPEN_FILE_REQUEST = 101

        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, AddProductsActivity::class.java)
        }
    }


}
