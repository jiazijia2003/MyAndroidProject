package com.example.myadroidproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myadroidproject.R

class FooterFragment:Fragment() {
private lateinit var totalTV:TextView
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view= inflater.inflate(R.layout.fragment_footer, container, false)
//             totalTV= view.findViewById<TextView>(R.id.footerTV)
//            val total=arguments?.getString("Total")?:"0.0"
//            view.findViewById<TextView>(R.id.footerTV).text=total
            totalTV= view.findViewById(R.id.footerTV)
            return view
        }

//

    //update the total

    fun getTotal(total:Double){

        totalTV.text="Total Expenses: CAD${total}"


    }
}