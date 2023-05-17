package com.example.diplom.data

data class Participant(
    val startNumber: String? = "0",
    val fio: String? = "Имя не указано",
    var checkedStatus: Boolean = false
)
