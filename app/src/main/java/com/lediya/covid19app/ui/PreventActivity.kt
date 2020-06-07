package com.lediya.covid19app.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lediya.covid19app.R
import com.lediya.covid19app.databinding.ActivityPreventBinding

class PreventActivity :BaseActivity(){
    private lateinit var  binding:ActivityPreventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_prevent)
        setupToolBar()
    }
    private fun setupToolBar() {
        setSupportActionBar(binding.toolbar)
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}