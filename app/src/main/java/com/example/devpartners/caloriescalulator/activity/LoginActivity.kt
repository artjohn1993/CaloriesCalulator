package com.example.devpartners.caloriescalulator.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.event.LoginEvent
import com.example.devpartners.caloriescalulator.event.RegisterEvent
import com.example.devpartners.caloriescalulator.event.SignInEvent
import com.example.devpartners.caloriescalulator.event.SignUpEvent
import com.example.devpartners.caloriescalulator.fragment.LoginFragment
import com.example.devpartners.caloriescalulator.fragment.RegisterFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        changeFragment(LoginFragment())
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
        nextActivity()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSignUp(event : SignUpEvent) {
        nextActivity()
    }

    private fun nextActivity() {
        startActivity<DashBoardActivity>()
        finish()
    }
    fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()
        fragment.setCustomAnimations(R.anim.abc_slide_in_bottom, android.R.animator.fade_out)
        fragment.replace(R.id.frameLogin, data).commit()
    }
}
