package com.example.movietrivia

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TriviaQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var question: TextView
    private lateinit var option1: TextView
    private lateinit var option2: TextView
    private lateinit var option3: TextView
    private lateinit var option4: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var questionList: List<Question>
    private var curSelected: Int = 1
    private var curPosition = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_questions)
        question = findViewById(R.id.tv_question)
        option1 = findViewById(R.id.tv_option_one)
        option2 = findViewById(R.id.tv_option_two)
        option3 = findViewById(R.id.tv_option_three)
        option4 = findViewById(R.id.tv_option_four)
        tvProgress = findViewById(R.id.tv_progress)
        progressBar = findViewById(R.id.progress_bar)
        lifecycleScope.launch {
            questionList = apiCall()
            Log.d("TriviaQuestionsActivity", questionList.size.toString())
            setQuestion()
            setListeners()
        }
    }

    private suspend fun apiCall(): List<Question> {
        return TriviaData(this).getTriviaQuestions()
    }

    private fun setQuestion() {
        question.text = questionList[0].question
        option1.text = questionList[0].answers[0]
        option2.text = questionList[0].answers[1]
        option3.text = questionList[0].answers[2]
        option4.text = questionList[0].answers[3]
        tvProgress.text = "$curPosition" + "/" + progressBar.max
        progressBar.progress = 1
        setDefaultOptionsView()
    }

    private fun setDefaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, option1)
        options.add(1, option2)
        options.add(2, option3)
        options.add(3, option4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_bg
            )
        }
    }

    private fun setListeners() {
        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(option1, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(option2, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(option3, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(option4, 4)
            }
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
        // Reset selection when user clicks another option
        setDefaultOptionsView()
        curSelected = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_bg
        )
    }


}