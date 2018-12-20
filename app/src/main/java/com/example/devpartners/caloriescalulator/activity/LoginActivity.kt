package com.example.devpartners.caloriescalulator.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.database.DatabaseHandler
import com.example.devpartners.caloriescalulator.event.*
import com.example.devpartners.caloriescalulator.fragment.LoginFragment
import com.example.devpartners.caloriescalulator.fragment.RegisterFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    var database = DatabaseHandler(this)
    var totalUser = 0
    var currentQuery = 0
    var loginCount = 0
    var isEmailExist = false
    var isregisterDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        changeFragment(LoginFragment())
        countUser()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRegister(event : RegisterEvent) {
        changeFragment(RegisterFragment())
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogin(event : LoginEvent) {
        changeFragment(LoginFragment())
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSignIn(event : SignInEvent) {
        if (event.email == "" && event.password == "" ) {
            CustomSnackBar.show("Please fill up completely!", this)
        } else {
            validateLogin(event)
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSignUp(event : SignUpEvent) {
        isregisterDone = false
        if(event.fullname != "" && event.phone != "" && event.email != "" && event.passwword != "" && event.confirmPassword != "") {
            var isvalidEmail = event.email.isValidEmail()
            if (isvalidEmail) {
                if (checkPassword(event.passwword, event.confirmPassword)) {
                    if (event.phone.count() == 11) {
                        queryEmail(event.email, event)
                    }
                    else {
                        CustomSnackBar.show("Invalid phone number!", this)
                    }
                }
                else {
                    CustomSnackBar.show("Invalid confirm password!", this)
                }
            }
            else {
                CustomSnackBar.show("Invalid Email!", this)
            }
        }
        else {
            CustomSnackBar.show("Please complete the form!", this)
        }

    }

    private fun validateLogin(event : SignInEvent) {
        var firebaseUserInfo = FirebaseDatabase.getInstance().reference
                .child("user_info")

        firebaseUserInfo.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                setData(p0, event.email, event.password)
                loginCount++
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }
    private fun setData(data : DataSnapshot?, emailCon : String, passwordCon : String) {
        var i = data!!.children.iterator()
        var checker : Boolean = false
        while (i.hasNext()) {
            var email = i.next().value.toString()
            var fullname = i.next().value.toString()
            var password = i.next().value.toString()
            var phone = i.next().value.toString()
            if (email == emailCon && password == passwordCon) {
                database.insertUserInfo(email,phone,fullname)
                nextActivity()
                checker = true
                break
            }
            else {
                if (totalUser == loginCount) {
                    loginCount = 0
                    CustomSnackBar.show("Invalid email or password!", this)
                }
            }
        }
    }
    private fun checkPassword(password : String , confirmPass : String) : Boolean {
        return password == confirmPass
    }
    private fun registerUser(event : SignUpEvent) {
        var root = FirebaseDatabase.getInstance().reference
        var tempkey = root.child("user_info") .push().key
        var map = HashMap<String, String>()
        var map2 = HashMap<String, String>()

        map.put("email", event.email)
        map.put("fullname", event.fullname)
        map.put("password", event.passwword)
        map.put("phone", event.phone)

        root.child("user_info")
                .updateChildren(map2 as Map<String, Any>)

        isregisterDone = true
        root.child("user_info")
                .child(tempkey!!)
                .updateChildren(map as Map<String, Any>).addOnCompleteListener {

                    successActivity()
                }.addOnFailureListener {
                    println("failed register")
                }
    }
    private fun nextActivity() {
        startActivity<DashBoardActivity>()
        finish()
    }
    private fun successActivity() {
        startActivity<RegSuccessActivity>()
        finish()
    }

    private fun checkEmail(event : SignUpEvent,emailData : String,data : DataSnapshot?) {
        currentQuery++
        var i = data!!.children.iterator()
        while (i.hasNext()) {
            var email = i.next().value.toString()
            var fullname = i.next().value.toString()
            var password = i.next().value.toString()
            var phone = i.next().value.toString()
            if (email == emailData) {
                isEmailExist = true
                break
            }
        }

        if (currentQuery == totalUser) {
            if (isEmailExist) {
                if (!isregisterDone) {
                    CustomSnackBar.show("Email already exist", this)
                    isEmailExist = false
                    currentQuery = 0
                }
            } else {
                registerUser(event)
            }
        }
    }

    private fun queryEmail(email : String, event : SignUpEvent ) {
        var firebaseUserInfo = FirebaseDatabase.getInstance().reference
                .child("user_info")

        firebaseUserInfo.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                checkEmail(event, email, p0)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }

    private fun countUser() {
        var firebaseUserInfo = FirebaseDatabase.getInstance().reference
                .child("user_info")

        firebaseUserInfo.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                totalUser++
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }


        })
        println("")
    }

    fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()
        fragment.setCustomAnimations(R.anim.abc_slide_in_bottom, android.R.animator.fade_out)
        fragment.replace(R.id.frameLogin, data).commit()
    }

    fun String.isValidEmail(): Boolean
            = this.isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
