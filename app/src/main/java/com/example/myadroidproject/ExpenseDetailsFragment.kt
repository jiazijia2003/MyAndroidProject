package com.example.myadroidproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

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
        val currency=arguments?.getString("Currency")?:"CAD"
        val convertedCost=arguments?.getString("ConvertedCost")?:"No need for conversion"
        view.findViewById<TextView>(R.id.tvname).text = name
        view.findViewById<TextView>(R.id.tvamount).text = "Cost: CAD"+amount
        view.findViewById<TextView>(R.id.tvdate).text = date
        view.findViewById<TextView>(R.id.tvIsChecked).text=currency
        view.findViewById<TextView>(R.id.tvConvertedCost).text="Cost: ${currency}$convertedCost"
return view
    }
}