package com.example.diplom.data

class Repository {
    private val photoService = PhotoService.create()
    private val m1Service = M1Service.create()
    suspend fun getRaces(dateFrom: String, dateTo: String) = photoService.getRaces(dateFrom, dateTo)
    suspend fun getRaceInfo(uid: String) = m1Service.getRaceInfo(uid)
}