package com.example.agtgallery.splashscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import com.example.agtgallery.MainActivity
import com.example.agtgallery.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if(Build.VERSION.SDK_INT < 30){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        }else{
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        }

        Handler(Looper.myLooper()!!).postDelayed(object : Runnable{
            override fun run() {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)
    }
}