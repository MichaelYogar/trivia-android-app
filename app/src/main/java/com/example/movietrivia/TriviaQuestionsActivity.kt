package com.example.movietrivia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class TriviaQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_questions)
        apiCall()
    }

    private fun apiCall() {
        val url = "https://cat-fact.herokuapp.com/facts"
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            {
                Log.d("TriviaQuestionsActivity", it.getJSONObject(0).toString())
            },
            {
                Log.d("TriviaQuestionsActivity", it.message.toString())
            }
        )
        queue.add(jsonArrayRequest)
    }

}