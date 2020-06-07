package com.lediya.covid19app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lediya.covid19app.R
import com.lediya.covid19app.databinding.ContactHelplineBinding
import com.lediya.covid19app.databinding.StateListItemRowBinding
import com.lediya.covid19app.model.ContactDetails

class HelpLineAdapter : RecyclerView.Adapter<HelpLineAdapter.ViewHolder>() {

    private lateinit var binding: ContactHelplineBinding
    private lateinit var stateList: List<ContactDetails>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = createBinding(parent)
        return ViewHolder(binding)
    }

    private fun createBinding(parent: ViewGroup): ContactHelplineBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.contact_helpline,
            parent,
            false
        )
    }

    /**
     * initialise adapter with data
     */
    fun setItems(itemList: List<ContactDetails>) {
        this.stateList = itemList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stateList[position]
        holder.binding.stateName.text = item.state
        holder.binding.contactNumber.text = item.helpLine
    }

    class ViewHolder(val binding: ContactHelplineBinding) :
        RecyclerView.ViewHolder(binding.root)
}