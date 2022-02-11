package com.trivia.movietrivia

import android.content.Context
import android.util.Log
import androidx.core.text.HtmlCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val URL_PATH = "https://opentdb.com/api.php?amount=10&category=11&type=multiple"

class TriviaData(private val ctx: Context) {

    suspend fun getTriviaQuestions() = suspendCoroutine<List<Question>> { cont ->
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, URL_PATH, null,
            {
                val results: JSONArray = it.getJSONArray("results")
                val questions: ArrayList<Question> = ArrayList()
                for (i in 0 until results.length()) {
                    val answers: ArrayList<String> = ArrayList()
                    val item = results.getJSONObject(i)
                    val correctAnswer: String = item["correct_answer"] as String
                    val incorrectAnswers = item.getJSONArray("incorrect_answers")
                    val question: String = htmlToString(item.getString("question"))

                    answers.add(htmlToString(correctAnswer))
                    for (j in 0 until incorrectAnswers.length()) {
                        answers.add(htmlToString(incorrectAnswers[j] as String))
                    }

                    answers.shuffle()
                    questions.add(
                        Question(
                            question = question,
                            correctAnswer = correctAnswer,
                            answers = answers
                        )
                    )
                }
                cont.resume(questions.toList())
            },
            {
                Log.d("TriviaQuestionsActivity", it.message.toString())
            }
        )
        TriviaManager.getInstance(ctx).addToRequestQueue(jsonObjectRequest)
    }

    private fun htmlToString(html: String): String {
        return HtmlCompat.fromHtml(
            html,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
    }

}