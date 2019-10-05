package com.ediwow.kmlrecorder

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, "kml", null, 1) {
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        p0!!.execSQL("DROP TABLE IF EXISTS history")
//        onCreate(p0)
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("CREATE TABLE history(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, km REAL NOT NULL, ltr REAL NOT NULL, cons REAL NOT NULL, date DATE NOT NULL)")
    }

    fun save(km: Float, ltr: Float, cons: Float) {
        try {
            val db = this.readableDatabase
            val cv = ContentValues()
            val cal = Calendar.getInstance()
            cv.put("km", km)
            cv.put("ltr", ltr)
            cv.put("cons", cons)
            cv.put("date", SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(cal.time))
            db.insert("history", null, cv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

     fun reload() : MutableList<String>{
        val ml : MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.query("history", arrayOf("cons", "date"), null, null, null, null, "id DESC")
        while (cursor.moveToNext()){
            ml.add("${cursor.getFloat(0)} - ${cursor.getString(1)}")
        }
        cursor.close()
        db.close()
        return ml
    }
}