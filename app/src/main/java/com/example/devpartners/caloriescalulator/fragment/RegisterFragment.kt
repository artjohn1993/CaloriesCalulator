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
    var nameEdit : android.support.design.widget.TextInputEditText? = null
    var numberEdit : android.support.design.widget.TextInputEditText? = null
    var emailEdit : android.support.design.widget.TextInputEditText? = null
    var passEdit : android.support.design.widget.TextInputEditText? = null
    var confirmpassEdit : android.support.design.widget.TextInputEditText? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_register, container, false)
        setvariables(view)
        login?.setOnClickListener {
            EventBus.getDefault().post(LoginEvent())
        }
        signup?.setOnClickListener {

            EventBus.getDefault().post(SignUpEvent(nameEdit?.text.toString(),
                    numberEdit?.text.toString(),
                    emailEdit?.text.toString(),
                    passEdit?.text.toString(),
                    confirmpassEdit?.text.toString()
                    ))
        }
        return view
    }

    private fun setvariables(view: View) {
        login = view.findViewById(R.id.backText)
        signup = view.findViewById(R.id.signup)
        nameEdit = view.findViewById(R.id.nameEdit)
        numberEdit = view.findViewById(R.id.numberEdit)
        emailEdit = view.findViewById(R.id.emailEdit)
        passEdit = view.findViewById(R.id.passEdit)
        confirmpassEdit = view.findViewById(R.id.confirmpassEdit)
    }
}
