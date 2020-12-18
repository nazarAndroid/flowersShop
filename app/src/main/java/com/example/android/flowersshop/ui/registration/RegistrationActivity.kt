package com.example.android.flowersshop.ui.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.flowersshop.R
import com.example.android.flowersshop.ui.login.LoginActivity
import com.example.android.flowersshop.ui.main.MainActivityFragment
import com.example.android.flowersshop.common.Status
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_regist.*
import kotlinx.android.synthetic.main.activity_regist.email
import kotlinx.android.synthetic.main.activity_regist.password
import kotlinx.android.synthetic.main.activity_regist.save

class RegistrationActivity : AppCompatActivity() {
    private lateinit var viewModelRegistration: ViewModelRegistration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        viewModelRegistration = ViewModelProviders.of(this).get(ViewModelRegistration::class.java)

        sign_in.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        save.setOnClickListener {
            registrationUser()
        }
        viewModelRegistration.userRegistration.observe(this, Observer {
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

    private fun registrationUser() {
        if (email.text.isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Please set name", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.text.length < 6) {
            Toast.makeText(this@RegistrationActivity, "Please set password", Toast.LENGTH_SHORT).show()
            return
        }
        if (number.text.isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Please set number", Toast.LENGTH_SHORT).show()
            return
        }
        else{
            viewModelRegistration.registrationUser(email.text.toString(), password.text.toString(), number.text.toString())
        }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent1 = Intent(this@RegistrationActivity, MainActivityFragment::class.java)
            startActivity(intent1)
            finish()
        }
    }
}