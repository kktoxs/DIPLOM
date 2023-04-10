package com.example.diplom.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface M1Service {
    @GET("race")
    suspend fun getRaceInfo(
        @Query("ruid") uid: String
    ): Response<FullRace>

    companion object {
        private const val BASE_URL = "https://marshalone.ru/api/public/"
        fun create(): M1Service {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(M1Service::class.java)
        }
    }
}