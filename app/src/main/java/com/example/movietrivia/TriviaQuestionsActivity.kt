package com.example.movietrivia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TriviaQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_questions)
        lifecycleScope.launch {
            val result = apiCall()
        }
    }

    private suspend fun apiCall() {
        TriviaData(this).getTriviaQuestions()
    }
}