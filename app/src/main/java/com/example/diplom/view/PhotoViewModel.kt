package com.example.diplom.view

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhotoViewModel: ViewModel() {
    private val _originalPhoto = MutableLiveData<Bitmap?>()
    val originalPhoto: MutableLiveData<Bitmap?> = _originalPhoto

    private val _originalPhotoList = MutableLiveData<List<Bitmap>?>()
    val originalPhotoList: MutableLiveData<List<Bitmap>?> = _originalPhotoList

    private val _editedPhoto = MutableLiveData<Bitmap?>()
    val editedPhoto: MutableLiveData<Bitmap?> = _editedPhoto

    private val _editedPhotoList = MutableLiveData<List<Bitmap>?>()
    val editedPhotoList: MutableLiveData<List<Bitmap>?> = _editedPhotoList

    var customHeight: Int? = null
    var customWidth: Int? = null
    var customScale: Double? = null

    fun setOriginalPhoto (newPhoto: Bitmap?) {
        originalPhoto.value = newPhoto
    }

    fun setOriginalPhoto (newPhotos: List<Bitmap>?) {
        originalPhotoList.value = newPhotos
    }

    fun setEditedPhoto (newPhoto: Bitmap?) {
        editedPhoto.value = newPhoto
    }

    fun setEditedPhoto (newPhotos: List<Bitmap>?) {
        editedPhotoList.value = newPhotos
    }


    companion object {
        const val WIDTH = 20
        const val HEIGHT = 20
        const val SCALE = 0.5
    }
}