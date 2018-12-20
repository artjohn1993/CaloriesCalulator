package com.example.devpartners.caloriescalulator.event

class SignUpEvent {
    var fullname : String
    var phone : String
    var email : String
    var passwword : String
    var confirmPassword : String

    constructor(fullname : String,
                phone : String,
                email : String,
                passwword : String,
                confirmPassword : String) {
        this.fullname = fullname
        this.phone = phone
        this.email = email
        this.passwword = passwword
        this.confirmPassword = confirmPassword
    }
}