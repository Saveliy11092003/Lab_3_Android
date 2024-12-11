package com.example.lab3.service

import android.util.Log
import com.example.lab3.adapter.CurrencyAdapter
import com.example.lab3.model.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class NetworkService(private var adapter: CurrencyAdapter) {

    private val parseService : ParseService = ParseService()


    fun httpRequest() {
        var currencies = mutableListOf<Currency>()
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://www.cbr-xml-daily.ru/daily_json.js")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        Log.e("NetworkService", "The request to the server was not successful")
                        throw IOException(
                            "The request to the server was not successful: ${response.code} ${response.message}"
                        )
                    }
                    Log.i("NetworkService", "The request to the server was successful")
                    val responseBody = response.body!!.string()
                    currencies = parseService.parseAllCurrencies(responseBody)

                    withContext(Dispatchers.Main) {
                        adapter.setCurrencies(currencies)
                    }
                    Log.d("NetworkService", "currencies - $currencies")
                }
            } catch (e: IOException) {
                Log.e("NetworkService", "Connection error: $e")
            }
        }
    }
}