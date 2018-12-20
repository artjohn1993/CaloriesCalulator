package com.example.devpartners.caloriescalulator.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.database.DatabaseHandler
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    var database = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
//                startActivity<LoginActivity>()
//                finish()
            if (database.checkSignInResult()) {
                startActivity<DashBoardActivity>()
                finish()
            }
            else {
                startActivity<LoginActivity>()
                finish()
            }
        }, 2000)
    }
}
