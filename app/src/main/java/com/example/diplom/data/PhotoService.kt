package com.example.diplom.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
  /*  @GET("getPreviews?limit=8")
    suspend fun getPreviews(
        @Query("raceUID") raceUID: String,
        @Query("offset") offset: Int
    ): Response<Previews>
*/
    @GET("getPreviews?limit=24")
    suspend fun getPreviews(
        @Query("raceUID") raceUID: String,
        @Query("offset") offset: Int,
        @Query("competitor") competitor: Int? = null
    ): Response<Previews>


    @GET("meta")
    suspend fun getMeta(
        @Query("UUID") raceUID: String,
    ): Response<Meta>

    companion object {
        private const val BASE_URL = "https://photo.marshalone.ru/api/photo/"
        fun create(): PhotoService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(PhotoService::class.java)
        }
    }
}