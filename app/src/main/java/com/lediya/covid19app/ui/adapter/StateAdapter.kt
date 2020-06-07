package com.lediya.covid19app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lediya.covid19app.R
import com.lediya.covid19app.databinding.StateListItemRowBinding
import com.lediya.covid19app.model.State

class StateAdapter : RecyclerView.Adapter<StateAdapter.ViewHolder>() {

    private lateinit var binding: StateListItemRowBinding
    private lateinit var stateList: List<State>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = createBinding(parent)
        return ViewHolder(binding)
    }

    private fun createBinding(parent: ViewGroup): StateListItemRowBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.state_list_item_row,
            parent,
            false
        )
    }

    /**
     * initialise adapter with data
     */
    fun setItems(itemList: List<State>) {
        itemList.forEach{
            item ->
            if(item.name.contains("Cases being reassigned to states")){
            }
        }
        this.stateList = itemList

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stateList[position]
        if(item.name !="Cases being reassigned to states") {
            holder.binding.stateName.text = item.name
            holder.binding.total.text = item.total.toString()
            holder.binding.death.text = item.death.toString()
            holder.binding.cured.text = item.cured.toString()
            holder.binding.active.text = item.active.toString()
        }
    }

    class ViewHolder(val binding: StateListItemRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}