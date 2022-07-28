package com.example.nycschoolsproject.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nycschoolsproject.R
import com.example.nycschoolsproject.SchoolDetailsActivity
import com.example.nycschoolsproject.databinding.SchoolListFragmentBinding
import com.example.nycschoolsproject.model.SchoolItem
import com.example.nycschoolsproject.viewmodel.SchoolListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * Displays the list of NYC schools with its dbn, schoolname and location to the user.
 */
@AndroidEntryPoint
class SchoolListFragment : Fragment() {

    private lateinit var binding: SchoolListFragmentBinding
    private val viewModel: SchoolListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SchoolListFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.items.observe(viewLifecycleOwner, ::onItemsLoaded)
        viewModel.itemSelected.observe(viewLifecycleOwner, ::onItemSelected)

        binding.swipeContainer.setOnRefreshListener(viewModel)
        return binding.root
    }

    private fun onItemsLoaded(list: List<SchoolItem>?) {
        // Check if list is empty or not, Ask user to refresh if data is not present.
        if (!list.isNullOrEmpty()) return
        Snackbar.make(binding.root, R.string.there_is_no_data, Snackbar.LENGTH_SHORT)
            .setAction(R.string.retry) {
            binding.swipeContainer.isRefreshing = true
            viewModel.onRefresh()
        }.show()
    }

    private fun onItemSelected(schoolDBN : String) {
        if (schoolDBN.isNotEmpty()) {
            // Starting another activity here to show selected school details based on DBN
            val intent = Intent(context, SchoolDetailsActivity::class.java)
            intent.putExtra("DBN", schoolDBN)
            startActivity(intent)
        }
    }

}