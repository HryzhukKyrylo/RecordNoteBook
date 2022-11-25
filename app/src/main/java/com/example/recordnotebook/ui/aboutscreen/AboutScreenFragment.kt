package com.example.recordnotebook.ui.aboutscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.recordnotebook.BuildConfig
import com.example.recordnotebook.databinding.FragmentAboutScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment

class AboutScreenFragment : BaseFragment<FragmentAboutScreenBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setVersionCode()
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setVersionCode() {
        val versionAppName = BuildConfig.VERSION_NAME
        binding.tvAppVersion.text = versionAppName
    }
}