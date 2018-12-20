package com.example.devpartners.caloriescalulator.activity

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ResultActivity : AppCompatActivity() {

    var weight = ""
    var height = ""
    var age = ""
    var minutes = ""
    var calories = ""
    var bmi = ""
    var database = DatabaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        getData()

        caloriesText.text = calories
        timeText.text = minutes + " minutes"
        //weight : String, time : String, calories : String,minutes : String

        val date = getCurrentDateTime()
        val dateInString = date.toString("MMMM d, yyyy HH:mm:ss")

        yesText.setOnClickListener {
            database.insertHistory(weight,height,age,bmi , dateInString,calories, minutes)
            startActivity<DashBoardActivity>()
            finish()
        }
        noText.setOnClickListener {
            startActivity<DashBoardActivity>()
            finish()
        }

        calculateBMI()

    }

    fun getData() {
        if (intent.extras != null) {
            weight = intent.getStringExtra("weight")
            var tempMinutes = intent.getStringExtra("minutes").toDouble()
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            minutes = tempMinutes.toInt().toString()
            calories = intent.getStringExtra("calories")
            height = intent.getStringExtra("height")
            age = intent.getStringExtra("age")
        }
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun calculateBMI() {
        var height = this.height.toFloat()
        var weight = this.weight.toFloat()

        height /= 100
        var bmi = weight / (height * height)

        displayBMI(bmi)
    }

    private fun displayBMI(bmi: Float) {
        var bmiLabel = ""

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight)
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(bmi, 16f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight)
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight)
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal)
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight)
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i)
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii)
        } else {
            bmiLabel = getString(R.string.obese_class_iii)
        }

        this.bmi = bmiLabel
        statusBMI.text = bmiLabel
    }
}
