package com.example.devpartners.caloriescalulator.enum

enum class CalCal {
    DATABASE_NAME { override fun getValue() = "CaloriesCalculator" },
    USER_INFO { override fun getValue() = "User_Info" },
    HISTORY { override fun getValue() = "History" };

    abstract fun getValue() : String
}

enum class Table_User_Info {

    EMAIL { override fun getValue() = "email" },
    FULLNAME { override fun getValue() = "fullname" },
    PHONE { override fun getValue() = "phone" };

    abstract fun getValue() : String
}

enum class Table_History {

    TIME { override fun getValue() = "time" },
    WEIGHT { override fun getValue() = "weight" },
    HEIGHT { override fun getValue() = "height" },
    AGE { override fun getValue() = "age" },
    BMI { override fun getValue() = "bmi" },
    MINUTES { override fun getValue() = "minutes" },
    CALORIES { override fun getValue() = "calories" };

    abstract fun getValue() : String
}