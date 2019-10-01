package com.sartorio.degas.ui.orderslist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.sartorio.degas.R
import com.sartorio.degas.model.Order
import com.sartorio.degas.ui.customcompoents.SearchableDialog
import com.sartorio.degas.ui.customcompoents.SearchableDialogClickListener
import com.sartorio.degas.ui.newclient.NewClientActivity
import com.sartorio.degas.ui.orderdetails.OrderDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class OrdersListActivity : AppCompatActivity(),
    OrderListClickListener, SearchableDialogClickListener {

    private val ordersListViewModel: OrdersListViewModel by viewModel()

    private val clientsDialog: SearchableDialog by lazy {
        SearchableDialog(this, ordersListViewModel.getClientNameList(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Pedidos Cadastrados"
        initView()
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


        recyclerViewOrdersList.adapter =
            OrdersAdapter(list, this)
    }

    private fun setupListeners() {
        buttonAddOrder.setOnClickListener {
            startActivity(NewClientActivity.createIntent(this))
            clientsDialog.show()
        }
    }

    private fun addNewOrder(clientName: String) {
        ordersListViewModel.addNewOrder(clientName)
    }


    override fun onClick(order: Order) {
        startActivity(OrderDetailsActivity.createIntent(this, order.clientName, order.date))
    }

    override fun onListItemClick(item: String) {
        addNewOrder(item)
        clientsDialog.dismiss()
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
