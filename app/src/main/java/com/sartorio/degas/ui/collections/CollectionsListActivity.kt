package com.sartorio.degas.ui.collections

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sartorio.degas.R
import com.sartorio.degas.ui.collections.addproducts.AddProductsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionsListActivity : AppCompatActivity() {
    private val collectionListViewModel: CollectionsListViewModel by viewModel()

    val deleteDialog: AlertDialog by lazy {
        AlertDialog.Builder(this)
            .setMessage("Deseja deletar todas as PEÇAS e PEDIDOS cadastrados? Essa ação não pode ser desfeita")
            .setPositiveButton("Sim") { dialog, which -> deleteAll() }
            .setNegativeButton("Não") { dialog, which -> dialog.dismiss() }
            .setTitle("ATENÇÃO")
            .create()
    }

    val successDialog : AlertDialog by lazy {
        AlertDialog.Builder(this)
            .setTitle("Sucesso")
            .setPositiveButton("OK"){dialog, which -> dialog.dismiss() }
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
    }

    private fun deleteAll() {
        loadingDialog.show()
        collectionListViewModel.deleteAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.collection_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addCollection -> startActivity(AddProductsActivity.createIntent(this))
            R.id.deleteAllProducts -> {
                deleteDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, CollectionsListActivity::class.java)
        }
    }
}
