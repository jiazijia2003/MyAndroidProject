package com.example.myadroidproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FooterFragment:Fragment() {
private lateinit var totalTV:TextView
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view= inflater.inflate(R.layout.fragment_footer, container, false)
             totalTV= view.findViewById<TextView>(R.id.footerTV)
            return view
        }

//

    //update the total

    @SuppressLint("SetTextI18n")
    fun getTotal(total:Double){
        totalTV.text="Total Expenses: ${total}"

    }
}