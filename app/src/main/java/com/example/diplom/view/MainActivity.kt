package com.example.diplom.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.diplom.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val photoViewModel: PhotoViewModel by viewModels()

    val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    val bitmapImage = convertToBitmap(uri)
                    photoViewModel.setOriginalPhoto(bitmapImage)
                }
            } else {
                println("Selecting picture cancelled")
            }
        }

    val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { list ->
            try {
                val bitmapImages = list.mapNotNull { convertToBitmap(it) }
                photoViewModel.setOriginalPhoto(bitmapImages)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }

        }

    var latestTmpUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigationView.setOnItemReselectedListener { it ->
            val selectedGraph = navController.graph.findNode(it.itemId) as NavGraph
            selectedGraph.let {
                navController.popBackStack(it.startDestinationId, false)
            }
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

    fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(applicationContext, PACKAGE_ID, tmpFile)
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        try {
//            when (requestCode) {
//                GET_FROM_GALLERY -> {
//                    if (resultCode == Activity.RESULT_OK) {
//                        val selectedImage = data?.data
//                        val bitmapImage = convertToBitmap(selectedImage)
//                        photoViewModel.setOriginalPhoto(bitmapImage)
//                    } else {
//                        println("Selecting picture cancelled")
//                    }
//                }
//
//                REQUEST_IMAGE_CAPTURE -> {
//                    if (resultCode == Activity.RESULT_OK) {
//                        val intentExtras = data?.extras
//                        if (intentExtras != null) {
//                            val bitmapImage = intentExtras.get("data") as Bitmap
//                            photoViewModel.setOriginalPhoto(bitmapImage)
//                        }
//                    } else {
//                        println("Taking photo cancelled")
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            println("Exception in onActivityResult : " + e.message)
//        }
//    }

    companion object {
        const val PACKAGE_ID = "com.example.diplom.provider"
    }


}