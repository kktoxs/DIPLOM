package com.example.diplom.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface M1Service {
    @GET("api/public/race")
    suspend fun getRaceInfo(
        @Query("ruid") uid: String
    ): Response<FullRace>

    @GET("api/public/races")
    suspend fun getRaces(
        @Query("date_from") date_from: String,
        @Query("date_to") date_to: String
    ): Response<RacesList>


    @GET("reports/classes")
    suspend fun getClasses(
        @Query("raceUid") raceUid: String
    ): Response<List<RaceClass>>

    @GET("reports/participants")
    suspend fun getParticipants(
        @Query("raceUid") raceUid: String,
        @Query("classUid") classUid: String
    ): Response<List<Participant>>

    companion object {
        private const val BASE_URL = "https://marshalone.ru/"
        fun create(): M1Service {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(M1Service::class.java)
        }
    }
}