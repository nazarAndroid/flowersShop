package com.example.android.flowersshop.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.flowersshop.R
import com.example.android.flowersshop.ui.main.MainActivityFragment
import com.example.android.flowersshop.ui.registration.RegistrationActivity
import com.example.android.flowersshop.common.Status
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModelLog: ViewModelLogin


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModelLog = ViewModelProviders.of(this).get(ViewModelLogin::class.java)
        updateUI(viewModelLog.isUserLogin())

        sign_up.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        save.setOnClickListener {
            if (email.text.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please set name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.text.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please set password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                viewModelLog.loginAccount(email.text.toString(), password.text.toString())
            }
        }
        viewModelLog.user.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    updateUI(it.data)
                }
                Status.ERROR -> {
                }
            }
        })
    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent1 = Intent(this@LoginActivity, MainActivityFragment::class.java)
            startActivity(intent1)
            finish()
        }
    }
}