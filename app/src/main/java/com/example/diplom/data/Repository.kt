package com.example.diplom.data

class Repository {
    private val photoService = PhotoService.create()
    private val m1Service = M1Service.create()
    suspend fun getRaces(dateFrom: String, dateTo: String) = m1Service.getRaces(dateFrom, dateTo)
    suspend fun getRaceInfo(uid: String) = m1Service.getRaceInfo(uid)

    //suspend fun getPreviews(uid: String, offset: Int) = photoService.getPreviews(uid, offset)
    suspend fun getPreviews(uid: String, offset: Int, competitor: Int?) =
        photoService.getPreviews(uid, offset, competitor)

    suspend fun getClasses(uid: String) = m1Service.getClasses(uid)
    suspend fun getParticipants(uid: String, raceClass: String) =
        m1Service.getParticipants(uid, raceClass)

    suspend fun getMeta(uuid: String) = photoService.getMeta(uuid)
}