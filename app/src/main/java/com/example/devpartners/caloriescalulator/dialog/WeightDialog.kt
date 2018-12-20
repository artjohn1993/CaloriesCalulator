package com.example.devpartners.caloriescalulator.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.activity.CameraActivity
import kotlinx.android.synthetic.main.dialog_get_weight.*

class WeightDialog : AppCompatActivity(){
    lateinit var dialog : Dialog
    lateinit var proceed: Button
    lateinit var cancel : Button
    lateinit var weight : EditText
    lateinit var height : EditText
    lateinit var age : EditText
    lateinit var status : TextView
    fun show(activity: Activity){
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_get_weight)
        proceed = dialog.findViewById(R.id.proceedButton)
        cancel = dialog.findViewById(R.id.cancelButton)
        weight = dialog.findViewById(R.id.weight)
        height = dialog.findViewById(R.id.height)
        age = dialog.findViewById(R.id.age)
        status = dialog.findViewById(R.id.status)


        cancel.setOnClickListener {
            dialog.hide()

        }
        proceed.setOnClickListener {

            if (weight.text.toString() == "" || age.text.toString() == "" || height.text.toString() == "") {
                status.text = "Fill up the form completely"
            }
            else {
                if (age.text.toString().toInt() < 18) {
                    status.text = "Minimum age should be 18"
                }
                else {
                    if(weight.text.toString().toInt() < 45) {
                        status.text = "Minimum weight should be 45 kl"
                    }
                    else {
                        if(height.text.toString().toInt() < 1) {
                            status.text = "Invalid height"
                        }
                        else {
                            dialog.hide()

                            var intent = Intent(activity,CameraActivity::class.java)
                            intent.putExtra("weight", weight.text.toString())
                            intent.putExtra("height", height.text.toString())
                            intent.putExtra("age", age.text.toString())
                            activity.startActivity(intent)
                            activity.finish()
                        }
                    }
                }
            }
        }

        dialog.show()
    }

}