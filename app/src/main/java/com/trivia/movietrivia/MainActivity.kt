package com.trivia.movietrivia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val button: Button = findViewById(R.id.btn_start)
        val editText: EditText = findViewById(R.id.et_name)
        button.setOnClickListener {
            if (editText.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("MainActivity", editText.text.toString())
                val intent = Intent(this, TriviaQuestionsActivity::class.java)
                intent.putExtra("username", editText.text.toString())
                startActivity(intent)
                finish()
            }
        }


    }
}