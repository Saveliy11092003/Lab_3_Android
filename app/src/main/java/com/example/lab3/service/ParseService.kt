package com.example.lab3.service

import android.util.Log
import com.example.lab3.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ParseService {

    suspend fun parseAllCurrencies(json: String, onError: (String) -> Unit): MutableList<Currency> {
        val currencies = mutableListOf<Currency>()
        try {
            val valute = getValuteObject(json)
            val keys = valute.keys()
            while (keys.hasNext()) {
                val currency = parseCurrency(valute, keys.next())
                if (currency != null) {
                    Log.d("ParseService", "Currency with name - ${currency.name}, value - ${currency.value} created")
                    currencies.add(currency)
                }
            }
        } catch (e: Exception) {
            handleParsingError(e, onError)
        }
        return currencies
    }

    private fun getValuteObject(json: String): JSONObject {
        val jsonObject = JSONObject(json)
        return jsonObject.getJSONObject("Valute")
    }

    private fun parseCurrency(valute: JSONObject, key: String): Currency? {
        val currencyObject = valute.getJSONObject(key)
        return if (currencyObject.has("Value") && currencyObject.has("Name")) {
            val name = currencyObject.getString("Name")
            val value = currencyObject.getDouble("Value")
            Currency(name, value)
        } else {
            null
        }
    }

     suspend fun handleParsingError(e: Exception, onError: (String) -> Unit) {
        Log.e("ParseService", "JSON parsing error: ${e.message}")
        withContext(Dispatchers.Main) {
            onError("JSON parsing error: ${e.message}")
        }
    }
}