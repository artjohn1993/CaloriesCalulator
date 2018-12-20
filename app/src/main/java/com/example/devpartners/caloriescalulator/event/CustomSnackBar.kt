package com.example.devpartners.caloriescalulator.event

import android.R
import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View

class CustomSnackBar {
    companion object {
        fun show(data : String, activity: Activity) {
            val snackbar = Snackbar.make(activity.findViewById<View>(R.id.content), data, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}