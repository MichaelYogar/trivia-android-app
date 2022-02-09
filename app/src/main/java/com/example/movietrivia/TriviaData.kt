package com.example.movietrivia

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest

class TriviaData(val ctx: Context) {

    fun getInfo() {
        val url = "https://cat-fact.herokuapp.com/facts"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                Log.d("TriviaQuestionsActivity", "success")
                Log.d("TriviaQuestionsActivity", it.getJSONObject(0).toString())
            },
            {
                Log.d("TriviaQuestionsActivity", it.message.toString())
            }
        )
        TriviaManager.getInstance(ctx).addToRequestQueue(jsonArrayRequest)
    }
}