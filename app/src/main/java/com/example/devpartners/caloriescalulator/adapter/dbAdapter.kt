package com.example.devpartners.caloriescalulator.adapter

import android.annotation.TargetApi
import android.os.Build
import android.provider.Settings.Global.getString
import android.support.constraint.R.id.parent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.model.HistoryModel

class dbAdapter(var data : MutableList<HistoryModel.Data>) : RecyclerView.Adapter<dbAdapter.dbViewHolder>() {


    var time : TextView? = null
    var weight : TextView? = null
    var calories : TextView? = null
    var minutes : TextView? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): dbViewHolder {
        val inflater = LayoutInflater.from(p0?.context)
        val layout = inflater.inflate(R.layout.layout_dashboard,p0, false)
        return dbViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(holder: dbViewHolder, position: Int) {
        var pos = getItemViewType(position)

        holder.time.text = data[pos].time
        holder.weight.text = "Weight : " + data[pos].weight
        holder.calories.text = "Burned calories : " + data[pos].calories
        holder.minutes.text =  "Duration : " + data[pos].minutes + " mins"
        holder.bmi.text = data[pos].bmi

    }

    class dbViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var time : TextView = itemView.findViewById(R.id.time)
        var weight : TextView = itemView.findViewById(R.id.weight)
        var calories : TextView = itemView.findViewById(R.id.calories)
        var minutes : TextView = itemView.findViewById(R.id.duration)
        var bmi : TextView = itemView.findViewById(R.id.bmiStatus)
    }
}


