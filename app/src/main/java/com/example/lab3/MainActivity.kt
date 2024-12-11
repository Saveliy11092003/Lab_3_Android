package com.example.lab3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.databinding.ActivityMainBinding
import com.example.lab3.adapter.CurrencyAdapter
import com.example.lab3.service.NetworkService


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
        NetworkService(adapter).httpRequest()
        Log.i("MainActivity", "Set currencies in CurrencyAdapter")

    }

    private fun initRecyclerView() {
        adapter = CurrencyAdapter { selectedCurrency ->
            updateConversion(selectedCurrency.value)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        Log.d("MainActivity", "Set layout and adapter in RecyclerView")
    }

    private fun updateConversion(currencyValue: Double) {
        val rublesText = binding.editTextNumberDecimal.text.toString()
        Log.d("MainActivity", "Rubles - $rublesText")
        val rubles = rublesText.toDoubleOrNull() ?: 0.0

        val convertedValue = rubles / currencyValue

        binding.result.text = String.format("%.2f", convertedValue)
        Log.i("MainActivity", "Update converted value : " + String.format("%.2f", convertedValue))
    }
}


