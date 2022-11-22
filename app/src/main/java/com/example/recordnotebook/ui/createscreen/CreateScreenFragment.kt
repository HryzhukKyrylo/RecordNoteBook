package com.example.recordnotebook.ui.createscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.FragmentCreateScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateScreenFragment : BaseFragment<FragmentCreateScreenBinding>() {

    private val args: CreateScreenFragmentArgs by navArgs()
    private val viewModel by viewModel<CreateScreenViewModel>()
    private var userModel: UserNotateModel? = null
    private var isCreate: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserData(args)
        userModel?.let {
            showData(it)
        }
        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.fabSave.setOnClickListener {
            saveUserData()
        }
    }

    private fun setUserData(args: CreateScreenFragmentArgs) {
        with(args) {
            userModel?.let { this@CreateScreenFragment.userModel = it }
            isCreate.let { this@CreateScreenFragment.isCreate = it }
        }
    }

    private fun initObservers() {
        with(viewModel) {
            isDataSaved.observe(viewLifecycleOwner) {
                if (it) {
                    requireContext().showToast(getString(R.string.create_screen_saved))
                    val action =
                        CreateScreenFragmentDirections.actionCreateScreenFragmentToMainScreenFragment()
                    findNavController().navigate(action)
                } else {
                    requireContext().showToast(getString(R.string.create_screen_something_wrong))
                }
            }
        }
    }

    private fun showData(notate: UserNotateModel) {
        binding.etLogData.setText(notate.logData)
        binding.etPassData.setText(notate.privateInfo)
    }

    private fun saveUserData() {
        val logData = binding.etLogData.text.toString()
        val passData = binding.etPassData.text.toString()
        viewModel.saveUserData(
            create = isCreate,
            logData = logData,
            passData = passData,
            userModel = userModel,
        )
    }
}