package com.example.recordnotebook.ui.splashscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.recordnotebook.databinding.FragmentSplashScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding>() {

    private val viewModel by viewModel<SplashScreenViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.goToNextScreen.observe(viewLifecycleOwner) {
            if (it) {
                val action =
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToSignInScreenFragment()
                findNavController().navigate(action)
            }
        }
        viewModel.nightMode.observe(viewLifecycleOwner) {
            when (it) {
                SharedPreferencesStorage.NIGHT_MODE_ON -> {

                }
                SharedPreferencesStorage.NIGHT_MODE_OFF -> {

                }
                SharedPreferencesStorage.NIGHT_MODE_SYSTEM -> {

                }
            }
        }
    }


}