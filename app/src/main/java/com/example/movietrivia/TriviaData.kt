package com.example.movietrivia

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val URL_PATH = "https://opentdb.com/api.php?amount=10&category=11&type=multiple"

class TriviaData(val ctx: Context) {
    suspend fun getTriviaQuestions() = suspendCoroutine<Question>{ cont ->
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, URL_PATH, null,
            {
                val results: JSONArray = it.getJSONArray("results")

                for (i in 0 until 1) {
                    val item = results.getJSONObject(i)
                    Log.d("TriviaQuestionsActivity", item.toString())
                    cont.resume(Question(question = item.getString("question")))
                }
            },
            {
                Log.d("TriviaQuestionsActivity", it.message.toString())
            }
        )
        TriviaManager.getInstance(ctx).addToRequestQueue(jsonObjectRequest)
    }

}