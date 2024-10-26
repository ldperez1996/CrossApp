package com.example.crossplan.models

data class Exercise(
    val id: String? = "",
    val name: String? = "",
    val series: Int? = 0,
    val repetitions: Int? = 0,
    val restTime: Int? = 0,
    val muscleGroup: String? = ""
)

