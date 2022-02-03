package com.sgncreations.musiccompanion

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sgncreations.musiccompanion.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnter : View? = findViewById(R.id.tv_enter_button)

        btnEnter!!.setOnClickListener {
            val intent = Intent(this, TunerActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}