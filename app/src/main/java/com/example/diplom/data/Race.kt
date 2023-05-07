package com.example.diplom.data

data class RacesList(
    val data: List<Race>
)

data class Race(
    val city: String,
    val competitorsCount: Int,
    val date: String,
    val logo: String,
    val name: String,
    val orgTeam: String,
    val sportType: String,
    val titlePicture: String,
    val uid: String
)


data class FullRace(
    val data: Data
)

data class Data(
    val Images: List<Images>,
    val city: String,
    val date: String,
    val sportType: String,
    val competitorsCount: Int,
    val description: String,
    val logo: String,
    val titlePicture: String,
    val name: String
)

data class Images(
    val comment: String,
    val uid: String
)