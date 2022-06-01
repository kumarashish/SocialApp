package com.dwara.socialmedia.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.dwara.socialmedia.MainActivity
import com.dwara.socialmedia.R
import com.dwara.socialmedia.databinding.ActivityMainBinding
import com.dwara.socialmedia.databinding.ActivitySplashBinding

class Splash  : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            handleUI()
            // close this activity
            finish()
        }, 4500)
    }

    private fun handleUI()
    {   var intent: Intent?=null
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}