package com.example.myadroidproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

@Suppress("UNREACHABLE_CODE")
class ExpenseDetailsFragment :Fragment(){
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


        view.findViewById<TextView>(R.id.tvname).text = name
        view.findViewById<TextView>(R.id.tvamount).text = amount.toString()
        view.findViewById<TextView>(R.id.tvdate).text = date
return view
    }
}