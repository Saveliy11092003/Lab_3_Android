package com.example.lab3.service

import android.util.Log
import com.example.lab3.model.Currency
import org.json.JSONObject

class ParseService {

    fun parseAllCurrencies(json: String): MutableList<Currency> {

        val currencies = mutableListOf<Currency>()

        try {
            val jsonObject = JSONObject(json)
            val valute = jsonObject.getJSONObject("Valute")
            val keys = valute.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val currencyObject = valute.getJSONObject(key)
                if (currencyObject.has("Value") && currencyObject.has("Name")) {
                    val name = currencyObject.getString("Name")
                    val value = currencyObject.getDouble("Value")
                    Log.d("ParseService", "Currency with name - $name, value - $value created")
                    currencies.add(Currency(name, value))
                }
            }
        } catch (e: Exception) {
            Log.e("ParseService", "JSON parsing error: ${e.message}")
        }

        return currencies
    }
}