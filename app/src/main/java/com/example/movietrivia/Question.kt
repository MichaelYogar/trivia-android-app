package com.example.movietrivia

data class Question(
    val question: String,
    val correctAnswer: String,
    val answers: List<String>
)