package com.example.movietrivia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var tvScore: TextView
    private lateinit var tvUsername: TextView
    private lateinit var btnPlayAgain: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val username = intent.getStringExtra("username")
        val score = intent.getStringExtra("score")
        tvScore = findViewById(R.id.tv_score)
        tvUsername = findViewById(R.id.tv_username)
        btnPlayAgain = findViewById(R.id.btn_play_again)
        tvUsername.text = username
        tvScore.text = score
        btnPlayAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}