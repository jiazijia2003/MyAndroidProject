package com.example.myadroidproject

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.Currency
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

import java.util.Locale
import kotlin.math.exp


private const val FILE_NAME = "expenses.txt"
class MainFragment:Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var footerFragment: FooterFragment
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var name:EditText
    private lateinit var amount:EditText
    private lateinit var date:TextView
    private lateinit var addBtn:Button
    private lateinit var tipBtn:Button
    //private lateinit var dateBtn:Button
    private lateinit var currencySpinner: Spinner
    private lateinit var checkbox:CheckBox
   // var total:Double=0.0
    private val calendar = Calendar.getInstance()
    private val expenseList= mutableListOf<Expense>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_main, container, false)
       // view.postDelayed({ showTotal() }, 300)
        //total=arguments?.getDouble("Total")?:0.0
        Handler(Looper.getMainLooper()).postDelayed({showTotal()},500)

        recyclerView=view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        expenseAdapter= ExpenseAdapter(expenseList,requireContext(),this)
        recyclerView.adapter=expenseAdapter
        name=view.findViewById(R.id.name)
        amount=view.findViewById(R.id.amount)
        date=view.findViewById(R.id.date)
        addBtn=view.findViewById(R.id.buttonAdd)
        tipBtn=view.findViewById(R.id.buttonTip)
        currencySpinner=view.findViewById(R.id.currencySpinner)
        checkbox=view.findViewById(R.id.checkBox)
        //populate the currency spinner
        val currencies=Currency.getAvailableCurrencies().map { it.currencyCode }.sorted()
        val adapter=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencySpinner.adapter=adapter
        val defaultIndex = currencies.indexOfFirst { it.toString() == "CAD" }

        if (defaultIndex >= 0) {
            currencySpinner.setSelection(defaultIndex)
        }
       // footerFragment =FooterFragment()
        //start give functions to buttons
        addBtn.setOnClickListener{

            val expenseName=name.text.toString()
            val expenseAmount=amount.text.toString()
            val currency=Currency.getInstance(currencySpinner.selectedItem.toString().uppercase())
            val isNeedConverted=checkbox.isChecked

            if(isNeedConverted){
                lifecycleScope.launch {
                    try {
                        val costs= withContext(Dispatchers.IO){
                            RetrofitInstance.api.getCurrencies()
                        }
                        if(costs.cad.isNotEmpty()){
                            //first i have to get the currency letters from the spinner
                            //then i use the currency letter to match the rate from the api json and return it
                            val selected=currencySpinner.selectedItem.toString()
                            Log.d("currency",selected)
                            val currenyRate=costs.cad[selected] ?:1.0
                           val convertedCost=currenyRate * expenseAmount.toDouble()
                            val expense = Expense(
                                expenseName,
                                expenseAmount,
                                date.text.toString(),
                                currency,
                                convertedCost
                            )
                            expenseList.add(expense)

                            expenseAdapter.notifyItemInserted(expenseList.size - 1)
                            saveExpensesToFile(requireContext(), expenseList)
                            name.text.clear()
                            amount.text.clear()
                            showTotal()

                            val message="${selected} ${currenyRate}"
                            Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                        }else{
                            Snackbar.make(requireView(), "No currency found", Snackbar.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        Snackbar.make(requireView(), "Error: ${e.message}", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }else {
               // convertedValue = expenseAmount.toDouble()

                if (expenseName.isEmpty() || expenseAmount.isEmpty() || expenseAmount.toDoubleOrNull() == null) {
                    Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
                } else {
                    val expense = Expense(
                        expenseName,
                        expenseAmount,
                        date.text.toString(),
                        currency,
                        expenseAmount.toDouble()
                    )
                    expenseList.add(expense)

                    expenseAdapter.notifyItemInserted(expenseList.size - 1)
                    saveExpensesToFile(requireContext(), expenseList)


                }
                name.text.clear()
                amount.text.clear()
                showTotal()
//            Handler(Looper.getMainLooper()).postDelayed({showTotal()},500)
                //  view.postDelayed({ showTotal() }, 300)
            }
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

 fun saveExpensesToFile(context: Context, taskList: List<Expense>) {
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
            Log.d("FileStorage", loadedTasks.toString())
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
    fun getDetails(index:Int){
        val expense=expenseList[index]

        val bundle=Bundle().apply {
            putString("Name", expense.name)
            putString("Amount",expense.amount)
            putString("Date", expense.date)
            putString("Currency", expense.currency.currencyCode)
            putString("ConvertedCost", expense.convertedCost.toString())
        }
        findNavController().navigate(R.id.action_mainFragment_to_details,bundle)
        saveExpensesToFile(requireContext(), expenseList)
      //  Log.d("InGetDetails", "expense.currency.toString()")
    }
    fun showTotal() {
        val total = expenseList.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }

        (activity as? MainActivity)?.updateTotal(total)
//        footerFragment?.getTotal(total)
//        if (footerFragment == null) {
//
//        } else {
//            footerFragment.getTotal(total)
//        }
    }
    private fun fetchCostConversion(){
        lifecycleScope.launch {
            try {
                val costs= withContext(Dispatchers.IO){
                    RetrofitInstance.api.getCurrencies()
                }
                if(costs.cad.isNotEmpty()){
                   //first i have to get the currency letters from the spinner
                    //then i use the currency letter to match the rate from the api json and return it
                    val selected=currencySpinner.selectedItem.toString().toLowerCase(Locale.ROOT)

                    val currenyRate=costs.cad[selected]
                    val message="${selected} ${currenyRate}"
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
                }else{
                    Snackbar.make(requireView(), "No currency found", Snackbar.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Snackbar.make(requireView(), "Error: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


//    override fun onStart() {
//        super.onStart()
//        showTotal()
//    }
}