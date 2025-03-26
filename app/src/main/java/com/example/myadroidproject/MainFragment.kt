package com.example.myadroidproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


private const val FILE_NAME = "expenses.txt"
class MainFragment:Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private val expenseList= mutableListOf<Expense>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main, container, false)


        expenseList.clear()
        expenseList.addAll(loadExpensesFromFile(requireContext()))

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
recyclerView=view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        expenseAdapter= ExpenseAdapter(expenseList,requireContext(),requireContext() as MainActivity)
        recyclerView.adapter=expenseAdapter
        // Observe the saved state handle for new or edited tasks
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("newExpense")
            ?.observe(viewLifecycleOwner) { bundle ->
                val updatedExpense =
                    Expense(
                        bundle.getString("expenseName", ""),
                        bundle.getString("expenseAmount", ""), bundle.getString("expenseDate", "")
                )


                    expenseList.add(updatedExpense)
                    expenseAdapter.notifyDataSetChanged()
                saveExpensesToFile(requireContext(), expenseList)


                // Save the updated list to file

            }
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


}