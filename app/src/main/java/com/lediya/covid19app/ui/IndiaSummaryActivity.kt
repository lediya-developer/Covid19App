package com.lediya.covid19app.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lediya.covid19app.R
import com.lediya.covid19app.communication.errorhandler.Status
import com.lediya.covid19app.databinding.ActivitySummaryBinding
import com.lediya.covid19app.model.IndiaSummary
import com.lediya.covid19app.viewmodel.SummaryViewModel

class IndiaSummaryActivity :BaseActivity(){
    private lateinit var binding:ActivitySummaryBinding
    private lateinit var viewModel: SummaryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView()
        setupToolBar()
        observer()
    }
    private fun bindingView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary)
        viewModel =
            ViewModelProviders.of(this).get(SummaryViewModel::class.java)
    }
    private fun observer(){
        if(isConnectedToNetwork(this)){
            viewModel.downloadIndiaCurrentData().observe(this, Observer { networkResource ->
                when (networkResource.status) {
                    Status.LOADING -> {
                        binding.spinKit.visibility = View.VISIBLE
                        binding.dataLayout.visibility = View.GONE
                        binding.warningError.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.spinKit.visibility = View.GONE
                        binding.dataLayout.visibility = View.VISIBLE
                        binding.warningError.visibility = View.GONE
                        val summaryData:IndiaSummary = networkResource.data as IndiaSummary
                        showData(summaryData)
                    }
                    Status.ERROR -> {
                        binding.spinKit.visibility = View.GONE
                        binding.dataLayout.visibility = View.GONE
                        binding.warningError.visibility = View.VISIBLE
                        binding.warningError.text = getString(R.string.server_down)
                    }
                }
            })
        }else{
            binding.spinKit.visibility = View.GONE
            binding.dataLayout.visibility = View.GONE
            binding.warningError.visibility = View.VISIBLE
            binding.warningError.text = getString(R.string.internet)
        }

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
    private fun showData(summaryData:IndiaSummary?){
        binding.spinKit.visibility = View.GONE
        binding.dataLayout.visibility = View.VISIBLE
        if(summaryData!=null){
            if(summaryData.active_case.isNotEmpty()){
                binding.active.text=summaryData.active_case
            }
            if(summaryData.cured.isNotEmpty()){
                binding.cured.text=summaryData.cured
            }
            if(summaryData.death.isNotEmpty()){
                binding.death.text=summaryData.death
            }
            if(summaryData.total.isNotEmpty()){
                binding.total.text=summaryData.total
            }
        }
    }
}