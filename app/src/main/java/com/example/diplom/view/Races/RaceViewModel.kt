package com.example.diplom.view.Races

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.data.FullRace
import com.example.diplom.data.Meta
import com.example.diplom.data.Participant
import com.example.diplom.data.Previews
import com.example.diplom.data.RacesList
import com.example.diplom.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RaceViewModel() : ViewModel() {
    val raceList = MutableLiveData<RacesList>()
    val currRaceInfo = MutableLiveData<FullRace?>()
    val currentDates = MutableLiveData<Pair<String, String>>()
    val currentParticipant = MutableLiveData<Int?>()
    val currentMeta = MutableLiveData<Meta>()
    val currentPhoto = MutableLiveData<String>()

    val previews = MutableLiveData<Previews?>()

    val participants = MutableLiveData<List<Participant>>()
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
        currRaceInfo.postValue(null)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRaceInfo(uid)
            currRaceInfo.postValue(response.body())
        }
    }

    /*  fun getPreviews(uid: String, offset: Int) {
          viewModelScope.launch(Dispatchers.IO) {
              val response = repository.getPreviews(uid, offset)
              previews.postValue(response.body())
              Log.d("current race uid", uid)
          }
      }*/

    fun getPreviews(uid: String, offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPreviews(
                uid, offset,
                currentParticipant.value?.toInt()
            )
            Log.d("ViewModel", "getPreviews " + response.body().toString())
            previews.postValue(response.body())
        }
    }

    fun getAllParticipants(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getClasses(uid)
            Log.d("classes", response.body().toString())
            if (response.body()?.isNotEmpty() == true) {
                participants.postValue(listOf())
                for (raceClass in response.body()!!) {
                    getParticipants(uid, raceClass.uid)
                    Thread.sleep(100)
                    //Log.d("get participant from ", raceClass.uid)
                }
            }
        }
    }

    private fun getParticipants(uid: String, raceClass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getParticipants(uid, raceClass)
            addParticipants(response.body() ?: listOf())
            //Log.d("participants in getParticipants", response.body()?.size.toString())
        }
    }

    private fun getMeta(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMeta(uid)
            currentMeta.postValue(response.body())
        }
    }

    fun openPhotoMeta(uid:String){
        currentPhoto.postValue(uid)
        getMeta(uid)
    }

    private fun addParticipants(newParticipants: List<Participant>) {
        var newList = mutableListOf<Participant>()
        if (participants.value?.isNotEmpty() == true)
            newList = participants.value as MutableList
        newList += (newParticipants)
        participants.postValue(newList)
        //Log.d("plused list", newList.size.toString())
    }

    fun selectDates(from: String, to: String) {
        currentDates.postValue(Pair(from, to))
    }

    fun clearPreviews() {
        previews.postValue(null)
    }

    fun clearParticipant() {
        currentParticipant.postValue(null)
    }

    fun selectParticipant(participant: Participant) {
        Log.d("ViewModel", "selectParticipant " + participant.startNumber.toString())
        currentParticipant.postValue(participant.startNumber?.toInt())
    }
}