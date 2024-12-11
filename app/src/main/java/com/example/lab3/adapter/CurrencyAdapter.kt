package com.example.lab3.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.R
import com.example.lab3.databinding.CurrencyItemBinding
import com.example.lab3.model.Currency

class CurrencyAdapter(private val onCurrencySelected: (Currency) -> Unit) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    private val currencies = mutableListOf<Currency>()
    private var selectedPosition: Int = -1

    class CurrencyHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = CurrencyItemBinding.bind(item)

        fun bind(currency: Currency, isSelected: Boolean, onRadioButtonClicked: (Int) -> Unit) {
            binding.textView.text = currency.name
            binding.radioButton.isChecked = isSelected

            binding.radioButton.setOnClickListener {
                onRadioButtonClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        Log.d("CurrencyAdapter", "Created view for currency_item")
        return CurrencyHolder(view)
    }

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(
            currencies[position],
            isSelected = position == selectedPosition
        ) { newPosition ->
            val previousPosition = selectedPosition
            selectedPosition = newPosition

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            onCurrencySelected(currencies[newPosition])
        }
        Log.d("CurrencyAdapter", "Binding was successful")
    }

    fun setCurrencies(newCurrencies: List<Currency>) {
        currencies.clear()
        currencies.addAll(newCurrencies)
        notifyDataSetChanged()
    }

}