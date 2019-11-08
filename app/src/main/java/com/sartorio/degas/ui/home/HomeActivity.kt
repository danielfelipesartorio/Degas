package com.sartorio.degas.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sartorio.degas.R
import com.sartorio.degas.ui.clients.ClientsListActivity
import com.sartorio.degas.ui.collections.CollectionsListActivity
import com.sartorio.degas.ui.orders.orderslist.OrdersListActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    fun onClickCollections(view: View) {
        startActivity(CollectionsListActivity.createIntent(this))
    }
    fun onClickClients(view: View) {
        startActivity(ClientsListActivity.createIntent(this))
    }
    fun onClickOrders(view: View) {
        startActivity(OrdersListActivity.createIntent(this))
    }
}
