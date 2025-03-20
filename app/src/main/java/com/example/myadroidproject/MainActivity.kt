package com.example.myadroidproject

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils.replace
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
   private lateinit var footerFragment: FooterFragment
    private lateinit var adapter: ExpenseAdapter
    var expenseList= mutableListOf(
        Expense("Clothes","50.99","2023-01-01"),
        Expense("rental","600.0","2023-01-01")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//https://www.manulife.ca/personal/plan-and-learn/healthy-finances/financial-planning/ten-simple-money-management-tips.html
       // addFragmentH()
    //   footerFragment =FooterFragment()
     //   addFragmentF()

        val name=findViewById<EditText>(R.id.nameText)
        val amount=findViewById<EditText>(R.id.amountText)
       // val date=findViewById<EditText>(R.id.dateText)
        val btPickDate=findViewById<Button>(R.id.dateButton);
        tvDate=findViewById<TextView>(R.id.tvDate)
        val addButton=findViewById<Button>(R.id.addButton)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
       // val deleteButton=findViewById<Button>(R.id.deleteButton)

//mDatePickerDialogFragment = new tutorials.droid.datepicker.DatePicker();
//                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        adapter=ExpenseAdapter(expenseList,this,this)
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
            showTotal()
        }
val tipButton=findViewById<Button>(R.id.tipButton)
        tipButton.setOnClickListener {
            var url =
                "https://www.manulife.ca/personal/plan-and-learn/healthy-finances/financial-planning/ten-simple-money-management-tips.html"
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        addFragmentH()
        footerFragment =FooterFragment()
        addFragmentF()
 //   showTotal()//always crash cuz make the total instance before the footer fragment done
//Handler(Looper.getMainLooper()).postDelayed({
//   // do something
//}, 1000)
Handler(Looper.getMainLooper()).postDelayed({showTotal()},500)
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
    //
//    val fragment = ProductListFragment()
//    supportFragmentManager
//    .beginTransaction()
//    .add(R.id.main_content, fragment)
//    .commit()   from documentation
    private fun addFragmentH(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentheader,HeaderFragment())
            .commit()
    }

private fun addFragmentF(){
   footerFragment = FooterFragment()
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentfooter,footerFragment)
        .commitNow()
    supportFragmentManager.executePendingTransactions()


}

    fun showTotal() {
        var total = 0.0
        for (i in expenseList) {
            total += i.amount.toDoubleOrNull() ?:0.0
        }
//        val footer=supportFragmentManager.findFragmentById(R.id.fragmentfooter) as? FooterFragment
//        footer?.getTotal(total)
       footerFragment.getTotal(total)
    }
}