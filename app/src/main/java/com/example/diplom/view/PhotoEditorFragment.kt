package com.example.diplom.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.diplom.R
import com.example.diplom.databinding.FragmentChoosePhotoBinding
import com.example.diplom.databinding.FragmentPhotoEditorBinding
import com.example.resizemodule.resizeUtils.Resizer
import com.example.resizemodule.resizeUtils.Scaler


class PhotoEditorFragment : Fragment() {

    private val viewModel: PhotoViewModel by activityViewModels()
    private lateinit var binding: FragmentPhotoEditorBinding

    private lateinit var fiterManager: FilterManager
    private lateinit var resizer: Resizer
    private lateinit var scaler: Scaler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoEditorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setWidth.doOnTextChanged { text, start, before, count ->
            if(text?.trim()?.isNotEmpty() == true) {
                val newWidth = parseToInt(text.trim())
                if (newWidth != null) {
                    viewModel.customWidth = newWidth
                }
            }
        }

        binding.setHeight.doOnTextChanged { text, start, before, count ->
            if(text?.trim()?.isNotEmpty() == true) {
                val newHeight = parseToInt(text.trim())
                if (newHeight != null) {
                    viewModel.customHeight = newHeight
                }
            }
        }

        binding.setScale.doOnTextChanged { text, start, before, count ->
            if(text?.trim()?.isNotEmpty() == true) {
                val newScale = parseToDouble(text.trim())
                if (newScale != null) {
                    viewModel.customScale = newScale
                }
            }
        }



    }

    private fun parseToInt(charSeq: CharSequence): Int? {
        return try {
            val parsedInt = charSeq.toString().toInt()
            if (parsedInt > 0) parsedInt
            else null
        } catch (e: Exception) {
            null
        }
    }

    private fun parseToDouble(charSeq: CharSequence): Double? {
        return try {
            val parsedDouble = charSeq.toString().toDouble()
            if (parsedDouble > 0) parsedDouble
            else null
        } catch (e: Exception) {
            null
        }
    }


}