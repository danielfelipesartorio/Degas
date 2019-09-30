package com.sartorio.degas.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sartorio.degas.R
import com.sartorio.degas.ui.orderslist.OrdersListActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            startActivity(OrdersListActivity.createIntent(this))
            finish()
        },1500)

        super.onPostCreate(savedInstanceState)
    }
}
