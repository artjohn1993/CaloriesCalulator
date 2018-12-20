package com.example.devpartners.caloriescalulator.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.TabHost
import com.example.devpartners.caloriescalulator.enum.CalCal
import com.example.devpartners.caloriescalulator.enum.Table_History
import com.example.devpartners.caloriescalulator.enum.Table_User_Info
import com.example.devpartners.caloriescalulator.model.HistoryModel

class DatabaseHandler(val context : Context) : SQLiteOpenHelper(context, CalCal.DATABASE_NAME.getValue(), null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + CalCal.USER_INFO.getValue() + " (" +
                Table_User_Info.EMAIL.getValue() + " VARCHAR(200)," +
                Table_User_Info.FULLNAME.getValue() + " VARCHAR(50)," +
                Table_User_Info.PHONE.getValue() + " VARCHAR(50))"
        )

        db?.execSQL("CREATE TABLE " + CalCal.HISTORY.getValue() + " (" +
                Table_History.TIME.getValue() + " VARCHAR(200)," +
                Table_History.CALORIES.getValue() + " VARCHAR(50)," +
                Table_History.WEIGHT.getValue() + " VARCHAR(50)," +
                Table_History.HEIGHT.getValue() + " VARCHAR(50)," +
                Table_History.AGE.getValue() + " VARCHAR(50)," +
                Table_History.BMI.getValue() + " VARCHAR(50)," +
                Table_History.MINUTES.getValue()+ " VARCHAR(50))"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertUserInfo(email : String, phone : String, fullname : String): Boolean {
        val db = this.writableDatabase
        var cvUser = ContentValues()
        cvUser.put(Table_User_Info.EMAIL.getValue(), email)
        cvUser.put(Table_User_Info.PHONE.getValue(), phone)
        cvUser.put(Table_User_Info.FULLNAME.getValue(), fullname)
        var result1 = db.insert(CalCal.USER_INFO.getValue(), null, cvUser)
        return result1 != (-1).toLong()
    }

    fun insertHistory(weight : String,height : String,age : String,bmi : String, time : String, calories : String,minutes : String): Boolean {
        val db = this.writableDatabase
        var cvUser = ContentValues()
        cvUser.put(Table_History.WEIGHT.getValue(), weight)
        cvUser.put(Table_History.HEIGHT.getValue(), height)
        cvUser.put(Table_History.AGE.getValue(), age)
        cvUser.put(Table_History.BMI.getValue(), bmi)
        cvUser.put(Table_History.TIME.getValue(), time)
        cvUser.put(Table_History.CALORIES.getValue(), calories)
        cvUser.put(Table_History.MINUTES.getValue(), minutes)

        var result1 = db.insert(CalCal.HISTORY.getValue(), null, cvUser)
        return result1 != (-1).toLong()
    }

    fun checkSignInResult() : Boolean {
        val list : MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val result1 = db.rawQuery("SELECT * from " + CalCal.USER_INFO.getValue(), null)
        if (result1.moveToFirst()) {
            do {
                list.add(result1.getString(result1.getColumnIndex(Table_User_Info.FULLNAME.getValue())))
            } while (result1.moveToNext())
        }
        return list.size != 0
    }

    fun getHistory() : MutableList<HistoryModel.Data> {
        val list : MutableList<HistoryModel.Data> = ArrayList()
        val db = this.readableDatabase
        val result1 = db.rawQuery("SELECT * from " + CalCal.HISTORY.getValue(), null)
        if (result1.moveToFirst()) {
            do {
                var data = HistoryModel.Data(
                        result1.getString(result1.getColumnIndex(Table_History.WEIGHT.getValue())),
                        result1.getString(result1.getColumnIndex(Table_History.HEIGHT.getValue())),
                        result1.getString(result1.getColumnIndex(Table_History.AGE.getValue())),
                        result1.getString(result1.getColumnIndex(Table_History.BMI.getValue())),
                        result1.getString(result1.getColumnIndex(Table_History.TIME.getValue())),
                        result1.getString(result1.getColumnIndex(Table_History.CALORIES.getValue())),
                        result1.getString(result1.getColumnIndex(Table_History.MINUTES.getValue()))
                )
                list.add(data)
            } while (result1.moveToNext())
        }
        return list
    }

}