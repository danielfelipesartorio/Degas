package com.sartorio.degas.ui.orders.orderslist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.Order
import com.sartorio.degas.ui.collections.addproducts.AddProductsActivity
import com.sartorio.degas.ui.clients.newclient.NewClientActivity
import com.sartorio.degas.ui.orders.orderdetails.OrderDetailsActivity
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import com.sartorio.degas.ui.customcompoents.SampleSearchModel
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class OrdersListActivity : AppCompatActivity(),
    OrderListClickListener{

    private val ordersListViewModel: OrdersListViewModel by viewModel()

    private val clientsDialog: SimpleSearchDialogCompat<SampleSearchModel> by lazy {
        SimpleSearchDialogCompat(
            this,
            "Escolha o cliente",
            "",
            null,
            ArrayList(ordersListViewModel.getClientNameList().map { com.sartorio.degas.ui.customcompoents.SampleSearchModel(it) }),
            SearchResultListener<SampleSearchModel> { _, item, _ ->
                addNewOrder(item?.title ?: return@SearchResultListener)
                clientsDialog.dismiss()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Pedidos Cadastrados"
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.orders_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.newClient -> startActivity(NewClientActivity.createIntent(this))
            R.id.addProducts -> startActivity(AddProductsActivity.createIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        recyclerViewOrdersList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        setupListeners()
        setupObservers()
        ordersListViewModel.initViewModel()
    }

    private fun setupObservers() {
        ordersListViewModel.ordersList.observe(this, Observer {
            setupAdapter(it)
        })
    }

    private fun setupAdapter(list: MutableList<Order>) {
        recyclerViewOrdersList.adapter = OrdersAdapter(list, this)
    }

    private fun setupListeners() {
        buttonAddOrder.setOnClickListener {
            clientsDialog.show()
        }
    }

    private fun addNewOrder(clientName: String) {
        ordersListViewModel.addNewOrder(clientName)
    }


    override fun onClick(order: Order) {
        startActivity(OrderDetailsActivity.createIntent(this, order.id))
    }

    override fun onLongPress(order: Order) {
        AlertDialog.Builder(this)
            .setTitle("Deletar pedido?")
            .setPositiveButton("Deletar") { _, _ -> removeOrder(order) }
            .setNegativeButton("Cancelar") { _, _ -> }
            .show()
    }

    private fun removeOrder(order: Order) {
        ordersListViewModel.deleteOrder(order)
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, OrdersListActivity::class.java)
        }
    }
}
