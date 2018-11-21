package com.example.devpartners.caloriescalulator.adapter

import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.devpartners.caloriescalulator.R

class dbAdapter() : RecyclerView.Adapter<dbAdapter.dbViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): dbViewHolder {
        val inflater = LayoutInflater.from(p0?.context)
        val layout = inflater.inflate(R.layout.layout_dashboard,p0, false)
        return dbViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(p0: dbViewHolder, p1: Int) {

    }

    class dbViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}


