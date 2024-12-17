package com.example.lab3

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.databinding.ActivityMainBinding
import com.example.lab3.adapter.CurrencyAdapter
import com.example.lab3.api.CurrencyApiClient
import com.example.lab3.utils.UiUtils


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("MainActivity", "Set Content View")

        initRecyclerView()
        Log.i("MainActivity", "Initializing RecyclerView")
        CurrencyApiClient(adapter){ errorMessage ->
            UiUtils.showError(this, errorMessage)
        }.httpRequest()
        Log.i("MainActivity", "Set currencies in CurrencyAdapter")

    }

    private fun initRecyclerView() {
        adapter = CurrencyAdapter { selectedCurrency ->
            val result = UiUtils.updateConversion(
                binding.editTextNumberDecimal.text.toString(),
                selectedCurrency.value
            )
            binding.result.text = result
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        Log.d("MainActivity", "Set layout and adapter in RecyclerView")
    }

}


