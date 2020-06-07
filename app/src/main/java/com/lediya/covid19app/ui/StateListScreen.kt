package com.lediya.covid19app.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lediya.covid19app.R
import com.lediya.covid19app.communication.errorhandler.Status
import com.lediya.covid19app.databinding.ActivityStateBinding
import com.lediya.covid19app.model.State
import com.lediya.covid19app.model.StateData
import com.lediya.covid19app.ui.adapter.StateAdapter
import com.lediya.covid19app.viewmodel.SummaryViewModel

class StateListScreen :BaseActivity(){
    private lateinit var binding:ActivityStateBinding
    private lateinit var viewModel: SummaryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_state)
        viewModel =
            ViewModelProviders.of(this).get(SummaryViewModel::class.java)
        setupToolBar()
        observer()
    }
    private fun observer(){
        if(isConnectedToNetwork(this)){
            viewModel.downloadStateWiseData().observe(this, Observer { networkResource ->
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
                        binding.recyclerView.visibility = View.GONE
                        binding.warningError.visibility = View.VISIBLE
                        binding.warningError.text =getString(R.string.server_down)
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
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showData(stateData: StateData?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = StateAdapter()
        if(stateData !=null){
            adapter.setItems(stateData.state)
            binding. recyclerView.addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            binding.recyclerView.adapter = adapter
        }

    }
}