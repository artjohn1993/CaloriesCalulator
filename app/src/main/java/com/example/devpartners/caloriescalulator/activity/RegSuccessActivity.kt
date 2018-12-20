package com.example.devpartners.caloriescalulator.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.devpartners.caloriescalulator.R
import kotlinx.android.synthetic.main.activity_reg_success.*
import org.jetbrains.anko.startActivity

class RegSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_success)

        successWrapper.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
    }
}
