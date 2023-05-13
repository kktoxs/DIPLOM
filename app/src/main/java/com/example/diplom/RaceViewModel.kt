package com.example.diplom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.data.FullRace
import com.example.diplom.data.Preview
import com.example.diplom.data.Race
import com.example.diplom.data.RacesList
import com.example.diplom.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RaceViewModel() : ViewModel() {
    val raceList = MutableLiveData<RacesList>()
    val currRaceInfo = MutableLiveData<FullRace?>()
    val currentDates = MutableLiveData<Pair<String, String>>()
    val previews = MutableLiveData<Preview?>()
    private val repository = Repository()


    init {
        currentDates.postValue(Pair("2022-07-09", "2022-07-17"))
    }

    fun getRaces(from: String, to: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRaces(from, to)
            raceList.postValue(response.body())
        }
    }

    fun getRaceInfo(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRaceInfo(uid)
            currRaceInfo.postValue(response.body())
        }
    }

    fun closeRace() {
        currRaceInfo.postValue(null)
    }

    fun getPreviews(uid: String, offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPreviews(uid, offset)

            previews.postValue(response.body())
        }
    }

    val galleryIsNull = MutableLiveData<Boolean>(true)
    fun checkPreviews(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPreviews(uid, 0)
            galleryIsNull.postValue(response.body() == null)
        }
    }

    fun selectDates(from: String, to: String) {
        currentDates.postValue(Pair(from, to))
    }

    fun clearPreviews() {
        previews.postValue(null)
    }
}