package com.doachgosum.marketsampleapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.doachgosum.marketsampleapp.constant.LogTag
import com.doachgosum.marketsampleapp.databinding.ActivityMainBinding
import com.doachgosum.marketsampleapp.presentation.util.getAppContainer
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            getAppContainer().marketRepository.getAllMarket()
                .also {
                    Log.d(LogTag.TAG_DEBUG, it.toString())
                }
        }
    }
}