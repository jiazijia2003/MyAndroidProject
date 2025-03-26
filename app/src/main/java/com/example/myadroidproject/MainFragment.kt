package com.example.myadroidproject

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

import java.util.Locale


private const val FILE_NAME = "expenses.txt"
class MainFragment:Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var name:EditText
    private lateinit var amount:EditText
    private lateinit var date:TextView
    private lateinit var addBtn:Button
    private lateinit var tipBtn:Button
    private lateinit var dateBtn:Button
    private val calendar = Calendar.getInstance()
    private val expenseList= mutableListOf<Expense>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        expenseAdapter= ExpenseAdapter(expenseList,requireContext(),this)
        recyclerView.adapter=expenseAdapter
        name=view.findViewById(R.id.name)
        amount=view.findViewById(R.id.amount)
        date=view.findViewById(R.id.date)
        addBtn=view.findViewById(R.id.buttonAdd)
        tipBtn=view.findViewById(R.id.buttonTip)

        //start give functions to buttons
        addBtn.setOnClickListener{
            val expenseName=name.text.toString()
            val expenseAmount=amount.text.toString()

            if(expenseName.isEmpty()||expenseAmount.isEmpty()||expenseAmount.toDoubleOrNull()==null){
              Toast.makeText(requireContext(),"Invalid input",Toast.LENGTH_SHORT).show()
            }else{
                val expense=Expense(expenseName,expenseAmount,date.text.toString())
                expenseList.add(expense)

                expenseAdapter.notifyItemInserted(expenseList.size-1)
                saveExpensesToFile(requireContext(),expenseList)
            }
            name.text.clear()
            amount.text.clear()
        }
        tipBtn.setOnClickListener{
                val url =
                    "https://www.manulife.ca/personal/plan-and-learn/healthy-finances/financial-planning/ten-simple-money-management-tips.html"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
        }
        expenseList.clear()
        expenseList.addAll(loadExpensesFromFile(requireContext()))
//        dateBtn.setOnClickListener{
//            val datePicker=com.example.myadroidproject.DatePicker()
//            datePicker.show(supportFragmentManager,"DATE PICK")
//        }
        date.apply {
            isFocusable = false
            isClickable = true
            setOnClickListener {
                showDatePickerDialog()
            }
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

private fun saveExpensesToFile(context: Context, taskList: List<Expense>) {
    try {
        val json = Gson().toJson(taskList)
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { output ->
            output.write(json.toByteArray())
        }
        Log.d("FileStorage", "Tasks saved successfully")
    } catch (e: IOException) {
        Log.e("FileStorage", "Error saving tasks: ${e.message}")
    }
}

    private fun loadExpensesFromFile(context: Context): MutableList<Expense> {
        val taskList: MutableList<Expense> = mutableListOf()
        try {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return taskList

            val json = file.readText()
            val type = object : TypeToken<List<Expense>>() {}.type
            val loadedTasks: List<Expense> = Gson().fromJson(json, type)
            taskList.addAll(loadedTasks)

            Log.d("FileStorage", "Tasks loaded successfully")
        } catch (e: FileNotFoundException) {
            Log.e("FileStorage", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error reading file: ${e.message}")
        }
        return taskList
    }
    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                date.text = formattedDate
            },
            year, month, day
        )

        datePickerDialog.show()
    }


}