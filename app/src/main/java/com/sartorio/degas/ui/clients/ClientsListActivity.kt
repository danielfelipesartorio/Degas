package com.sartorio.degas.ui.clients

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.Client
import com.sartorio.degas.ui.clients.importclient.ImportClientActivity
import com.sartorio.degas.ui.clients.newclient.NewClientActivity
import kotlinx.android.synthetic.main.activity_clients_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class ClientsListActivity : AppCompatActivity(), ClientsListOnClickListener {
    override fun onClick(client: Client) {
        startActivity(NewClientActivity.createIntent(this, client.name.companyName))
    }

    override fun onLongClick(client: Client) {
        AlertDialog.Builder(this)
            .setPositiveButton("Deletar") { dialog, which ->
                adapter.removerItem(client)
                clientsListViewModel.removeClient(client)
            }
            .show()
    }

    private lateinit var adapter: ClientsAdapter
    private val clientsListViewModel: ClientsListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients_list)
        initView()
    }

    override fun onResume() {
        clientsListViewModel.initViewModel()
        adapter.notifyDataSetChanged()
        super.onResume()
    }

    private fun initView() {
        recyclerViewClientsList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        setupListeners()
        setupObservers()
        setupAdapter(mutableListOf())
        clientsListViewModel.initViewModel()
    }

    private fun setupListeners() {
        buttonNewClient.setOnClickListener {
            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setPositiveButton("Importar") { dialog, which ->
                    startActivity(ImportClientActivity.createIntent(this))
                }
                .setNegativeButton("Cadastrar") { dialog, which ->
                    startActivity(NewClientActivity.createIntent(this))
                }
                .show()
        }
    }

    private fun setupObservers() {
        clientsListViewModel.clientsList.observe(this, Observer {
            adapter.list = it.toMutableList()
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupAdapter(list: MutableList<Client>) {
        adapter = ClientsAdapter(list, this)
        recyclerViewClientsList.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, ClientsListActivity::class.java)
        }
    }
}
