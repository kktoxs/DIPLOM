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
import com.example.diplom.databinding.FragmentChoosePhotoBinding
import com.example.diplom.view.PhotoViewModel.Companion.HEIGHT
import com.example.diplom.view.PhotoViewModel.Companion.WIDTH
import com.example.resizemodule.resizeUtils.Resizer
import java.io.File


class ChoosePhotoFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var filePhoto: File
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
                Resizer.resizeCommon(it, WIDTH, HEIGHT, true)
            }
        }
        binding.openGallery.setOnClickListener {
            openGallery()
        }
        binding.openCamera.setOnClickListener {
            openCamera()
        }
    }

    private fun openGallery() {
        activity?.startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            ), GET_FROM_GALLERY
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(FILE_NAME)
        val providerFile =
            FileProvider.getUriForFile(
                requireContext(),
                "com.example.diplom.fileprovider",
                filePhoto
            )
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        activity?.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName + System.currentTimeMillis(), ".jpg", directoryStorage)
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val GET_FROM_GALLERY = 3
        const val FILE_NAME = "photo"
    }
}