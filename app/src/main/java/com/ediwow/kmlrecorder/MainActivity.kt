package com.ediwow.kmlrecorder

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var edLiters: EditText
    private lateinit var edCKm: EditText
    private lateinit var btSave: Button
    private lateinit var lvHistory: ListView
    private lateinit var context: Context
    private lateinit var helper: DBHelper
    private lateinit var edPkm: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        helper = DBHelper(context)
        edLiters = findViewById(R.id.edL)
        edCKm = findViewById(R.id.edCKM)
        edPkm = findViewById(R.id.edPKM)
        btSave = findViewById(R.id.btSave)
        btSave.setOnClickListener {
            calculate(edCKm.text.toString(), edLiters.text.toString(), edPkm.text.toString())
        }
        lvHistory = findViewById(R.id.lvHistory)
        reload()
    }

    private fun calculate(Ckm: String, ltr: String, Pkm: String) {
        try {
            val fCkm = Ckm.toFloat()
            val fLtr = ltr.toFloat()
            var fPkm = 0f
            if (Pkm != "")
                fPkm = Pkm.toFloat()

            val cons = (fCkm - fPkm) / fLtr
            saveToDatabase(fCkm, fLtr, cons)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveToDatabase(km: Float, ltr: Float, cons: Float) {
        helper.save(km, ltr, cons)
        Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
        edCKm.setText("")
        edL.setText("")
        reload()
    }

    private fun reload() {
        val arrAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, helper.reload())
        lvHistory.adapter = arrAdapter
    }
}
