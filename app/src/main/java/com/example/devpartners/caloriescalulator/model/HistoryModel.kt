package com.example.devpartners.caloriescalulator.model

object HistoryModel {
    data class Data(
            var weight : String,
            var height : String,
            var age : String,
            var bmi : String,
            var time : String,
            var calories : String,
            var minutes : String
    )
}