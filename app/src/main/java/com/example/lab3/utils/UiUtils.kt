package com.example.lab3.utils

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast

object UiUtils {

    fun updateConversion(input:String, currencyValue: Double) : String {
        val rubles = input.toDoubleOrNull() ?: 0.0
        Log.d("UiUtils", "Rubles - $rubles")
        val convertedValue = rubles / currencyValue
        Log.i("UiUtils", "Update converted value : " + String.format("%.2f", convertedValue))
        return String.format("%.2f", convertedValue)
    }

     fun showError(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        Log.e("UiUtils", "Error: $message")
    }

}