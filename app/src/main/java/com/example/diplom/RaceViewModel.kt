package com.example.diplom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.data.FullRace
import com.example.diplom.data.Race
import com.example.diplom.data.RacesList
import com.example.diplom.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RaceViewModel(): ViewModel() {
    val raceList = MutableLiveData<RacesList>()
    val currRaceInfo = MutableLiveData<FullRace?>()

    private val repository = Repository()

    fun getRaces(from: String, to: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRaces(from, to)
            raceList.postValue(response.body())
        }
    }

    fun getRaceInfo(uid: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRaceInfo(uid)
            currRaceInfo.postValue(response.body())
            Log.d("fullRace", response.body().toString())
        }
    }
}