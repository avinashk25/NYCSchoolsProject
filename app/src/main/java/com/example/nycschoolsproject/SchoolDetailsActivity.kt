package com.example.nycschoolsproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.nycschoolsproject.databinding.ActivitySchoolDetailsBinding
import com.example.nycschoolsproject.viewmodel.SchoolDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Displays the SAT score details of the selected school.
 */

@AndroidEntryPoint
class SchoolDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySchoolDetailsBinding
    private val schoolDetailViewModel : SchoolDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_school_details)
        binding.lifecycleOwner = this
        binding.viewModel = schoolDetailViewModel

        // Extracting data received through the intent
        val intent = intent
        val dbn = intent.getStringExtra("DBN")
        // Consume HTTP API here to fetch School SAT score details here by calling in ViewModel
        dbn?.let { schoolDetailViewModel.updateSchoolDetails(it)  }
    }
}
