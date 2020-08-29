package com.example.nightnight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
            finish()
        }, SPLASH_TIME_OUT)
    }
}