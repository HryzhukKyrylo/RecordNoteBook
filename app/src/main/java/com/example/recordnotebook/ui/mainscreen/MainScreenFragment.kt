package com.example.recordnotebook.ui.mainscreen

import android.os.Bundle
import android.view.View
import com.example.recordnotebook.databinding.FragmentMainScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>() {

    private val viewModel by viewModel<MainScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
        initObservers()
    }

    private fun initObservers() {
        //todo implement
    }

    private fun initClickListener() {
        //todo implement
    }
}