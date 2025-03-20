package com.example.myadroidproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FooterFragment:Fragment() {
private  var totalTV:TextView?=null
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_footer, container, false)
            // totalTV= view.findViewById<TextView>(R.id.fragmentfooter)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalTV= view.findViewById<TextView>(R.id.fragmentfooter)
    }

    //update the total

    fun getTotal(total:Double){
        totalTV?.text="Total Expenses: ${total}"

    }
}