package com.example.devpartners.caloriescalulator.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.adapter.dbAdapter
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        setRecycler()
    }

    private fun setRecycler() {
        dbRecycker.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        dbRecycker.adapter = dbAdapter()
    }
}
