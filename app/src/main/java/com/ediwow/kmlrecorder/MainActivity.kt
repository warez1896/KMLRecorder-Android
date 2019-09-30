package com.ediwow.kmlrecorder

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var edLiters: EditText
    private lateinit var edKm: EditText
    private lateinit var btSave: Button
    private lateinit var lvHistory: ListView
    private lateinit var context: Context
    private lateinit var helper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        helper = DBHelper(context)
        edLiters = findViewById(R.id.edL)
        edKm = findViewById(R.id.edKM)
        btSave = findViewById(R.id.btSave)
        btSave.setOnClickListener {
            calculate(edKm.text.toString(), edLiters.text.toString())
        }
        lvHistory = findViewById(R.id.lvHistory)
        reload()
    }

    private fun calculate(km: String, ltr: String) {
        try {
            val fKm = km.toFloat()
            val fLtr = ltr.toFloat()
            val cons = fKm / fLtr
            saveToDatabase(fKm, fLtr, cons)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveToDatabase(km: Float, ltr: Float, cons: Float) {

        helper.save(km, ltr, cons)
        Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
        edKm.setText("")
        edL.setText("")
        reload()
    }

    private fun reload() {
        val arrAdapter =
            ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, helper.reload())
        lvHistory.adapter = arrAdapter
    }
}
