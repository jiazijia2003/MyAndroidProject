package com.example.myadroidproject

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{
    lateinit var tvDate: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val name=findViewById<EditText>(R.id.nameText)
        val amount=findViewById<EditText>(R.id.amountText)
       // val date=findViewById<EditText>(R.id.dateText)
        val btPickDate=findViewById<Button>(R.id.dateButton);
        tvDate=findViewById<TextView>(R.id.tvDate)
        val addButton=findViewById<Button>(R.id.addButton)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
       // val deleteButton=findViewById<Button>(R.id.deleteButton)
        var expenseList= mutableListOf(
            Expense("Clothes","50.99","2023-01-01"),
            Expense("rental","600.0","2023-01-01"),
            Expense("dinner","20.99","2023-01-01"),
            Expense("pet","200.99","2023-01-01")
        )
//mDatePickerDialogFragment = new tutorials.droid.datepicker.DatePicker();
//                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        val adapter=ExpenseAdapter(expenseList)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
        btPickDate.setOnClickListener{
            val datePicker=com.example.myadroidproject.DatePicker()
            datePicker.show(supportFragmentManager,"DATE PICK")
        }
        addButton.setOnClickListener{
            val expenseName=name.text.toString()
            val expenseAmount=amount.text.toString()

            if(expenseName.isEmpty()||expenseAmount.isEmpty()||expenseAmount.toDoubleOrNull()==null){
                Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show()
            }else{
            val expense=Expense(expenseName,expenseAmount,tvDate.text.toString())
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val selectedDate: String =
            DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime())
        tvDate?.setText(selectedDate)
    }
}