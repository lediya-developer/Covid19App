package com.lediya.covid19app.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lediya.covid19app.R
import com.lediya.covid19app.communication.errorhandler.Status
import com.lediya.covid19app.databinding.ActivityStateBinding
import com.lediya.covid19app.model.ContactDetails
import com.lediya.covid19app.model.StateData
import com.lediya.covid19app.model.StateHelpLine
import com.lediya.covid19app.ui.adapter.HelpLineAdapter
import com.lediya.covid19app.ui.adapter.StateAdapter
import com.lediya.covid19app.ui.utils.RecyclerItemClickListenr
import com.lediya.covid19app.viewmodel.SummaryViewModel

class HelpLineScreen :BaseActivity() {
    private lateinit var binding: ActivityStateBinding
    private lateinit var viewModel: SummaryViewModel
    private  var isCallCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_state)
        viewModel =
            ViewModelProviders.of(this).get(SummaryViewModel::class.java)
        setupToolBar()
        observer()
        checkPermission()
    }
    private fun observer(){
        if(isConnectedToNetwork(this)){
            viewModel.downloadHelpLineData().observe(this, Observer { networkResource ->
                when (networkResource.status) {
                    Status.LOADING -> {
                        binding.spinKit.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.warningError.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.spinKit.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.warningError.visibility = View.GONE
                        showData(networkResource.data)

                    }
                    Status.ERROR -> {
                        binding.spinKit.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.warningError.visibility = View.VISIBLE
                        binding.warningError.text = getString(R.string.server_down)
                    }
                }
            })
        }else{
            binding.spinKit.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.warningError.visibility = View.VISIBLE
            binding.warningError.text = getString(R.string.internet)
        }

    }
    private fun setupToolBar() {
        setSupportActionBar(binding.toolbar)
        binding.title.text= getString(R.string.help_line)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showData(helpLine: StateHelpLine ?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = HelpLineAdapter()
        if(helpLine !=null){
            adapter.setItems(helpLine.contactDetails)
            binding. recyclerView.addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            binding.recyclerView.adapter = adapter
        }
        binding.recyclerView.addOnItemTouchListener(RecyclerItemClickListenr(this,  binding.recyclerView, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                val phoneNumber = helpLine?.contactDetails?.get(position)?.helpLine
                if (phoneNumber != null) {
                    if(phoneNumber.isNotEmpty()){
                       if(phoneNumber.contains(",")){
                           callPhone(phoneNumber.split(",")[0])
                       }else{
                          callPhone(phoneNumber)
                       }
                    }
                }
            }
            override fun onItemLongClick(view: View?, position: Int) {
            }
        }))
    }
    fun callPhone(number:String){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this,"Please give permission call settings in app manager",Toast.LENGTH_LONG).show()
            return
        }
        startActivity(intent)
    }
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        }else{
            isCallCheck = true
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            isCallCheck = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            return
        }
    }
}