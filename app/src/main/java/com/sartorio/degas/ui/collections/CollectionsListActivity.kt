package com.sartorio.degas.ui.collections

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sartorio.degas.R
import com.sartorio.degas.ui.collections.addproducts.AddProductsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionsListActivity : AppCompatActivity() {
    private val collectionListViewModel: CollectionsListViewModel by viewModel()

    private val deleteDialog: AlertDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle(R.string.atention)
            .setMessage(R.string.deleteAllDialogMessage)
            .setPositiveButton(R.string.yes) { _, _ -> deleteAll() }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    private val successDialog: AlertDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle("Sucesso")
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()
    }
    private val loadingDialog: AlertDialog by lazy {
        AlertDialog.Builder(this, R.style.TransparentDialog).apply {
            setView(R.layout.loading_dialog)
        }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections_list)
        collectionListViewModel.deleteSuccess.observe(this, Observer {
            loadingDialog.dismiss()
            successDialog.show()
        })
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun deleteAll() {
        loadingDialog.show()
        collectionListViewModel.deleteAll()
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, CollectionsListActivity::class.java)
        }
    }

    fun onImportClick(view: View) {
        startActivity(AddProductsActivity.createIntent(this))
    }

    fun onDeleteClick(view: View) {
        deleteDialog.show()
    }
}
