package com.example.devpartners.caloriescalulator.fragment

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
import com.example.devpartners.caloriescalulator.event.LoginEvent
import com.example.devpartners.caloriescalulator.event.RegisterEvent
import com.example.devpartners.caloriescalulator.event.SignUpEvent
import org.greenrobot.eventbus.EventBus


class RegisterFragment : Fragment() {
    var login : TextView? = null
    var signup : Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_register, container, false)
        setvariables(view)
        login?.setOnClickListener {
            EventBus.getDefault().post(LoginEvent())
        }
        signup?.setOnClickListener {
            EventBus.getDefault().post(SignUpEvent())
        }
        return view
    }

    private fun setvariables(view: View) {
        login = view.findViewById(R.id.backText)
        signup = view.findViewById(R.id.signup)
    }
}
