package com.example.movietrivia

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TriviaQuestionsActivity : AppCompatActivity() {

    private lateinit var option1: TextView
    private lateinit var option2: TextView
    private lateinit var option3: TextView
    private lateinit var option4: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var result: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_questions)
        option1 = findViewById(R.id.tv_option_one)
        option2 = findViewById(R.id.tv_option_two)
        option3 = findViewById(R.id.tv_option_three)
        option4 = findViewById(R.id.tv_option_four)
        tvProgress = findViewById(R.id.tv_progress)
        progressBar = findViewById(R.id.progress_bar)
        lifecycleScope.launch {
            result = apiCall()
            Log.d("TriviaQuestionsActivity", result.size.toString())
            option1.text = result[0].question
            option2.text = result[1].question
            option3.text = result[2].question
            option4.text = result[3].question
            val currentPosition = 1
            tvProgress.text = "$currentPosition" + "/" + progressBar.max
            progressBar.progress = 1
        }
    }

    private suspend fun apiCall(): List<Question> {
        return TriviaData(this).getTriviaQuestions()
    }
}