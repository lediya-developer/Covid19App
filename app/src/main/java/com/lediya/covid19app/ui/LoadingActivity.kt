package com.lediya.covid19app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lediya.covid19app.R
import com.lediya.covid19app.databinding.LoadingMainBinding


class LoadingActivity : BaseActivity() {
    private lateinit var binding:LoadingMainBinding
    private  val handler: Handler = Handler()
    private val runnable = Runnable {
        launchHomeScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.loading_main)

        handler.postDelayed(runnable, 3000)
    }
    private fun launchHomeScreen(){
        handler.removeCallbacks(runnable)
        val i = Intent(this@LoadingActivity, HomeActivity::class.java)
        startActivity(i)
        finish()
    }
}
