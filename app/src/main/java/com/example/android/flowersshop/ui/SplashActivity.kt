package com.example.android.flowersshop.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.android.flowersshop.R
import com.example.android.flowersshop.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            animation.cancelAnimation()
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        },3000)
    }
}