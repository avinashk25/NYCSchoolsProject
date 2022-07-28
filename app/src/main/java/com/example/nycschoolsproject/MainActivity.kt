package com.example.nycschoolsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.nycschoolsproject.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The parent Activity for the app.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }
}