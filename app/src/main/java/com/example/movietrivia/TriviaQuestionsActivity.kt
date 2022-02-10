package com.example.movietrivia

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
    private lateinit var btnSubmit: Button

    private lateinit var questionList: List<Question>
    private var questionNumber = 1
    private var selectedIndex = -1
    private var selectedOption: TextView? = null
    private var totalCorrect = 0

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
        btnSubmit = findViewById(R.id.btn_submit)
        lifecycleScope.launch {
            questionList = apiCall()
            setQuestion()
            setListeners()
        }
    }

    private suspend fun apiCall(): List<Question> {
        return TriviaData(this).getTriviaQuestions()
    }

    private fun setQuestion() {
        if (questionNumber <= questionList.size){
            question.text = questionList[questionNumber - 1].question
            option1.text = questionList[questionNumber - 1].answers[0]
            option2.text = questionList[questionNumber - 1].answers[1]
            option3.text = questionList[questionNumber - 1].answers[2]
            option4.text = questionList[questionNumber - 1].answers[3]
            tvProgress.text = "$questionNumber" + "/" + progressBar.max
            progressBar.progress = questionNumber
            setDefaultOptionsView()
        }else{
            Log.d("TriviaQuestionsActivity", "I get here")
            btnSubmit.text = "Finished"
        }
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
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(option1, 0)
            }
            R.id.tv_option_two -> {
                selectedOptionView(option2, 1)
            }
            R.id.tv_option_three -> {
                selectedOptionView(option3, 2)
            }
            R.id.tv_option_four -> {
                selectedOptionView(option4, 3)
            }
            R.id.btn_submit -> {

                if (selectedIndex == -1) {
                    questionNumber++
                    btnSubmit.text = "Submit"
                    setQuestion()
                } else {
                    // Check correct answer
                    val currentQuestion: Question = questionList[questionNumber - 1]
                    if (currentQuestion.correctAnswer == currentQuestion.answers[selectedIndex]) {
                        selectedOption!!.setTextColor(Color.parseColor("#000000"))
                        selectedOption!!.setTypeface(selectedOption!!.typeface, Typeface.BOLD)
                        selectedOption!!.background = ContextCompat.getDrawable(
                            this, R.drawable.correct_option_bg
                        )
                        totalCorrect++
                    } else {
                        selectedOption!!.setTextColor(Color.parseColor("#000000"))
                        selectedOption!!.setTypeface(selectedOption!!.typeface, Typeface.BOLD)
                        selectedOption!!.background = ContextCompat.getDrawable(
                            this, R.drawable.incorrect_option_bg
                        )
                    }

                    // Reset value for next question
                    selectedIndex = -1

                    // Update button text for next question
                    btnSubmit.text = "Next Question"
                }

            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
        // Reset selection when user clicks another option
        setDefaultOptionsView()
        // Option that user chose
        selectedIndex = selectedOptionNumber
        selectedOption = tv
        tv.setTextColor(Color.parseColor("#000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_bg
        )
    }
}