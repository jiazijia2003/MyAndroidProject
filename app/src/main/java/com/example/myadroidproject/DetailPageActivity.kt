package com.example.myadroidproject

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myadroidproject.models.Expense

@Suppress("DEPRECATION")
class DetailPageActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.detail_page)

        //get the data from the recycle view
//cuz the Api
        val expense=if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("Detail", Expense::class.java)
        }else{
            intent.getSerializableExtra("Detail") as? Expense
        }

        val name=findViewById<TextView>(R.id.tvName)
        val date=findViewById<TextView>(R.id.tvDate)
        val amount=findViewById<TextView>(R.id.tvAmount)

        name.text=expense?.name
        date.text=expense?.date
        amount.text=expense?.amount
    }
}