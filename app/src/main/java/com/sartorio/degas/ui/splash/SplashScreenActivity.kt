package com.sartorio.degas.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sartorio.degas.R
import com.sartorio.degas.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            startActivity(HomeActivity.createIntent(this))
            finish()
        }, 1500)

        super.onPostCreate(savedInstanceState)
    }
}
