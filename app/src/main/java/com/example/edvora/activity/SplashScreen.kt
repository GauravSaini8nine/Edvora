package com.example.edvora.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.edvora.databinding.ActivitySplashScreenBinding
import com.example.edvora.listner.ApiInterface
import com.example.edvora.listner.ApiListener
import com.example.edvora.utils.APIClient
import com.example.edvora.utils.ApiRepo
import retrofit2.Retrofit

class SplashScreen : AppCompatActivity() {


    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        Handler().postDelayed({
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }, 2000)

        setContentView(binding.root)
    }


}