package com.example.devpartners.caloriescalulator.activity

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.adapter.dbAdapter
import com.example.devpartners.caloriescalulator.database.DatabaseHandler
import com.example.devpartners.caloriescalulator.dialog.WeightDialog
import kotlinx.android.synthetic.main.activity_dash_board.*
import org.jetbrains.anko.startActivity

class DashBoardActivity : AppCompatActivity() {

    var database = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        setRecycler()

        fabButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1000)
            }
            else {
                WeightDialog().show(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    WeightDialog().show(this)
                }
            }
        }
    }

    private fun setRecycler() {
        dbRecycker.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        dbRecycker.adapter = dbAdapter(database.getHistory().asReversed())
    }
}
