package com.example.myadroidproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private var expenseList:MutableList<Expense>,private var context: Context,private val activity: MainFragment):RecyclerView.Adapter<RecycleViewHolder>(){

   //inner  class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
//interface ExpenseListener{
//       fun onEditClick(expense: Expense)
//       fun onDeleteClick(expense: Expense)
//}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_expense,parent,false)
        return RecycleViewHolder(view)
    }

    override fun getItemCount(): Int {
       return expenseList.size
   }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.itemView.apply {
            val tvName = findViewById<TextView>(R.id.tvName)
            tvName.text=expenseList[position].name

            val tvAmount = findViewById<TextView>(R.id.tvAmount)
            tvAmount.text= expenseList[position].amount.toString()
            val tvDate=findViewById<TextView>(R.id.tvDate)
            tvDate.text=expenseList[position].date
            val deleteButton=findViewById<Button>(R.id.deleteButton)
            deleteButton.setOnClickListener{
                expenseList.removeAt(position)
                //notifyItemRemoved(position)
               // activity.showTotal()
                notifyDataSetChanged()
                activity.saveExpensesToFile(context,expenseList)
            }
            val detailsButton=findViewById<Button>(R.id.detailButton)
            detailsButton.setOnClickListener {
//               //    intent.putExtra("COUNTER_VALUE", counter) // add the data
//                //
//                //            // Start next activity
//                //            startActivity(intent)
                //use intent to pass the data
//
                activity.getDetails(position)
            }
        }
    }
}