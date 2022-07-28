package com.example.nycschoolsproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschoolsproject.databinding.SchoolListItemBinding
import com.example.nycschoolsproject.model.SchoolItem
import com.example.nycschoolsproject.viewmodel.SchoolListViewModel

/**
 * A RecyclerView.Adapter to populate the screen with a list of schools.
 */

class SchoolListAdapter(var items : List<SchoolItem> = emptyList(), val viewModel : SchoolListViewModel) : RecyclerView.Adapter<SchoolItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolItemViewHolder {
        val binding = SchoolListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolItemViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: SchoolItemViewHolder, position: Int) {
        holder.bindings.item = items.elementAtOrNull(position)
        holder.bindings.itemLayout.setOnClickListener{
            items.elementAtOrNull(position)?.let {
                //Pass the selected item to viewmodel
                viewModel.openSelectedSchoolDetails(it.dbn)
            }
        }
    }
}

/**
 * Holds the view for the School item.
 */

class SchoolItemViewHolder(val bindings: SchoolListItemBinding, itemView: View) : RecyclerView.ViewHolder(itemView)