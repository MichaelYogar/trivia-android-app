package com.example.movietrivia

import android.content.Context
import android.util.Log
import androidx.core.text.HtmlCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val URL_PATH = "https://opentdb.com/api.php?amount=10&category=11&type=multiple"

class TriviaData(val ctx: Context) {
    suspend fun getTriviaQuestions() = suspendCoroutine<List<Question>> { cont ->
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, URL_PATH, null,
            {
                val results: JSONArray = it.getJSONArray("results")
                val questions: MutableList<Question> = mutableListOf()
                for (i in 0 until results.length()) {
                    val item = results.getJSONObject(i)
                    val questionString : String = HtmlCompat.fromHtml(
                        item.getString("question"),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()

                    questions.add(Question(question = questionString))
                }
                cont.resume(questions.toList())
            },
            {
                Log.d("TriviaQuestionsActivity", it.message.toString())
            }
        )
        TriviaManager.getInstance(ctx).addToRequestQueue(jsonObjectRequest)
    }

}