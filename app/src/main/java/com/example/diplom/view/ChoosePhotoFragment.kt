package com.example.diplom.view

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.diplom.R
import com.example.diplom.databinding.FragmentChoosePhotoBinding
import com.example.diplom.view.PhotoViewModel.Companion.HEIGHT
import com.example.diplom.view.PhotoViewModel.Companion.WIDTH
import com.example.resizemodule.resizeUtils.Resizer
import kotlinx.coroutines.launch
import java.io.File


class ChoosePhotoFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
//    private lateinit var filePhoto: File
    private lateinit var binding: FragmentChoosePhotoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoosePhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.originalPhoto.observe(viewLifecycleOwner) {
            it.let { binding.testImage.setImageBitmap(it) }
            if (it != null) {
                Resizer.resizeRs(it, WIDTH, HEIGHT, requireContext())
            }
        }
        binding.openGallery.setOnClickListener {
            selectImageFromGallery()

        }
        binding.openCamera.setOnClickListener {
           takeImage()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            (activity as MainActivity).getTmpFileUri().let { uri ->
                (activity as MainActivity).latestTmpUri = uri
                (activity as MainActivity).takeImageResult.launch(uri)
            }
            if (viewModel.originalPhoto.value != null) navigateToEditor()
        }
    }

    private fun selectImageFromGallery() {
        lifecycleScope.launch {
            (activity as MainActivity).selectImageFromGalleryResult.launch(arrayOf("image/*"))
            if (!viewModel.originalPhotoList.value.isNullOrEmpty()) navigateToEditor()
        }
    }

    private fun navigateToEditor() {
        findNavController().navigate(R.id.action_choosePhotoFragment_to_photoEditorFragment)
    }


//    private fun getPhotoFile(fileName: String): File {
//        val directoryStorage = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(fileName + System.currentTimeMillis(), ".jpg", directoryStorage)
//    }

//    private fun openGallery() {
//        activity?.startActivityForResult(
//            Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI
//            ), GET_FROM_GALLERY
//        )
//    }
//
//    private fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        filePhoto = getPhotoFile(FILE_NAME)
//        val providerFile =
//            FileProvider.getUriForFile(
//                requireContext(),
//                "com.example.diplom.fileprovider",
//                filePhoto
//            )
////        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
//        activity?.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val GET_FROM_GALLERY = 3
        const val FILE_NAME = "photo"
    }
}