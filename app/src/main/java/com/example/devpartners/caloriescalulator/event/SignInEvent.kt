package com.example.devpartners.caloriescalulator.event

class SignInEvent {
    var email : String
    var password : String
    constructor(email : String, password : String) {
        this.email = email
        this.password = password
    }
}