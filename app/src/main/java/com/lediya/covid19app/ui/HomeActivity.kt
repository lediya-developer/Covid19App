package com.lediya.covid19app.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.lediya.covid19app.R
import com.lediya.covid19app.databinding.ActivityMainBinding
import com.lediya.covid19app.viewmodel.SummaryViewModel

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.app_name, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_about -> {
                launchSummaryScreen()
            }
            R.id.nav_prevent -> {
                launchPreventScreen()
            }
            R.id.nav_state -> {
                launchStateListScreen()
            }
            R.id.nav_help -> {
                launchHelpLineScreen()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun launchPreventScreen(){
        val i = Intent(this@HomeActivity, PreventActivity::class.java)
        startActivity(i)
    }
    private fun launchSummaryScreen(){
        val i = Intent(this@HomeActivity, IndiaSummaryActivity::class.java)
        startActivity(i)
    }
    private fun launchStateListScreen(){
        val i = Intent(this@HomeActivity, StateListScreen::class.java)
        startActivity(i)
    }
    private fun launchHelpLineScreen(){
        val i = Intent(this@HomeActivity, HelpLineScreen::class.java)
        startActivity(i)
    }
}