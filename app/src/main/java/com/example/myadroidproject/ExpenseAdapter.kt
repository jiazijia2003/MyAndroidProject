package com.example.myadroidproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private var expenses:MutableList<Expense>):RecyclerView.Adapter<ExpenseAdapter.ViewHolder>(){

   inner  class ViewHolder(view: View) : RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_expense,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return expenses.size
   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val tvName = findViewById<TextView>(R.id.tvName)
            tvName.text=expenses[position].name

            val tvAmount = findViewById<TextView>(R.id.tvAmount)
            tvAmount.text= expenses[position].amount.toString()
            val tvDate=findViewById<TextView>(R.id.tvDate)
            tvDate.text=expenses[position].date
            val deleteButton=findViewById<Button>(R.id.deleteButton)
            deleteButton.setOnClickListener{
                expenses.removeAt(position)
                notifyItemRemoved(position)
            }
            val detailsButton=findViewById<Button>(R.id.detailButton)
            detailsButton.setOnClickListener {
                val intent=Intent(holder.itemView.context,ExpenseDetailsActivity::class.java)
            }
        }
    }
}