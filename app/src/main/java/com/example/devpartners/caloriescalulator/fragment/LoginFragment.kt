package com.example.devpartners.caloriescalulator.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.example.devpartners.caloriescalulator.R
import com.example.devpartners.caloriescalulator.event.RegisterEvent
import com.example.devpartners.caloriescalulator.event.SignInEvent
import org.greenrobot.eventbus.EventBus


class LoginFragment : Fragment() {
    var register : TextView? = null
    var signin : Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        setvariables(view)

        register?.setOnClickListener {
            EventBus.getDefault().post(RegisterEvent())
        }
        signin?.setOnClickListener {
            EventBus.getDefault().post(SignInEvent())
        }
        return view
    }
    private fun setvariables(view: View) {
       register = view.findViewById(R.id.register)
        signin = view.findViewById(R.id.signin)
    }

}
