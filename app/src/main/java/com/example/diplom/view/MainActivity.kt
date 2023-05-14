package com.example.diplom.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.diplom.R
import com.example.diplom.view.ChoosePhotoFragment.Companion.GET_FROM_GALLERY
import com.example.diplom.view.ChoosePhotoFragment.Companion.REQUEST_IMAGE_CAPTURE
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val photoViewModel: PhotoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)
       /* bottomNavigationView.setOnItemSelectedListener { it ->
            val selectedGraph = navController.graph.findNode(it.itemId) as NavGraph
            selectedGraph.let {
                navController.popBackStack(it.startDestinationId, false)
            }
            //navController.popBackStack()
        }
        bottomNavigationView.setOnItemSelectedListener {
            navController.popBackStack()
        }*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            when (requestCode) {
                GET_FROM_GALLERY -> {
                    if (resultCode == Activity.RESULT_OK) {
                        val selectedImage = data?.data
                        val bitmapImage = convertToBitmap(selectedImage)
                        photoViewModel.setOriginalPhoto(bitmapImage)
                    } else {
                        println("Selecting picture cancelled")
                    }
                }

                REQUEST_IMAGE_CAPTURE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        val intentExtras = data?.extras
                        if (intentExtras != null) {
                            val bitmapImage = intentExtras.get("data") as Bitmap
                            photoViewModel.setOriginalPhoto(bitmapImage)
                        }
                    } else {
                        println("Taking photo cancelled")
                    }
                }
            }
        } catch (e: Exception) {
            println("Exception in onActivityResult : " + e.message)
        }
    }

    private fun convertToBitmap(selectedImage: Uri?): Bitmap? {

        return try {
            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
        } catch (e: FileNotFoundException) {
            println("Exception in onActivityResult : " + e.message)
            null
        } catch (e: IOException) {
            println("Exception in onActivityResult : " + e.message)
            null
        }

    }
}