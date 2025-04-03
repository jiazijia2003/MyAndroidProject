package com.example.myadroidproject.fragments

import android.annotation.SuppressLint
import android.icu.util.Currency
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myadroidproject.R

@Suppress("UNREACHABLE_CODE")
class ExpenseDetailsFragment :Fragment(){
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragement_details, container, false)

        //retrieve data
        val name=arguments?.getString("Name")?:"No Name"
        val amount=arguments?.getString("Amount")?:"No Amount"
        val date=arguments?.getString("Date")?:"No Date"
        var currency=arguments?.getString("Currency","CAD")?:"CAD"
        val convertedCost=arguments?.getString("ConvertedCost","0.0")?:"No need for conversion"
        Log.d("detailsFrag", currency.toString())
       //currency = Currency.getInstance(currency).symbol


        view.findViewById<TextView>(R.id.tvname).text = name
        view.findViewById<TextView>(R.id.tvamount).text = "Cost: CA$"+amount
        view.findViewById<TextView>(R.id.tvdate).text = date
        view.findViewById<TextView>(R.id.tvIsChecked).text=currency
        view.findViewById<TextView>(R.id.tvConvertedCost).text="Cost: ${Currency.getInstance(currency).symbol}$convertedCost"
return view
    }
}