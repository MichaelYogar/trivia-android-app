package com.trivia.movietrivia

data class Question(
    val question: String,
    val correctAnswer: String,
    val answers: List<String>
)