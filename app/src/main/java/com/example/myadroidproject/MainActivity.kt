package com.example.myadroidproject

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val name=findViewById<EditText>(R.id.nameText)
        val amount=findViewById<EditText>(R.id.amountText)
        val date=findViewById<EditText>(R.id.dateText)
        val addButton=findViewById<Button>(R.id.addButton)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
       // val deleteButton=findViewById<Button>(R.id.deleteButton)
        var expenseList= mutableListOf(
            Expense("Clothes","50.99"),
            Expense("rental","600.0"),
            Expense("dinner","20.99"),
            Expense("pet","200.99")
        )

        val adapter=ExpenseAdapter(expenseList)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        addButton.setOnClickListener{
            val expenseName=name.text.toString()
            val expenseAmount=amount.text.toString()

            if(expenseName.isEmpty()||expenseAmount.isEmpty()||expenseAmount.toDoubleOrNull()==null){
                Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
            }else{
            val expense=Expense(expenseName,expenseAmount)
            expenseList.add(expense)
            adapter.notifyItemInserted(expenseList.size-1)
            }
            name.text.clear()
            amount.text.clear()
        }



    }


    override fun onStart() {
        super.onStart()
        Log.d("ActivityLifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ActivityLifecycle", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ActivityLifecycle", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ActivityLifecycle", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActivityLifecycle", "onDestroy called")
    }
}