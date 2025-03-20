package com.example.myadroidproject

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewHolder(view: View):RecyclerView.ViewHolder(view) {
    var name=view.findViewById<TextView>(R.id.tvName)
    var amount=view.findViewById<TextView>(R.id.tvAmount)
    var date=view.findViewById<TextView>(R.id.tvDate)
    var delete=view.findViewById<Button>(R.id.deleteButton)
    var detail=view.findViewById<Button>(R.id.detailButton)
}