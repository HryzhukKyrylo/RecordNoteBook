package com.example.recordnotebook.ui.createscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.databinding.FragmentCreateScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

//todo add fab menu - add tittle
class CreateScreenFragment : BaseFragment<FragmentCreateScreenBinding>() {

    private val args: CreateScreenFragmentArgs by navArgs()
    private val viewModel by viewModel<CreateScreenViewModel>()
    private var userName: String? = null
    private var userModel: UserNotateModel? = null
    private var isCreate: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserData(args)
        userModel?.let {
            showData(it)
        }
        initObsorvers()
    }

    private fun setUserData(args: CreateScreenFragmentArgs) {
        with(args) {
            userName?.let { this@CreateScreenFragment.userName = it }
            userModel?.let { this@CreateScreenFragment.userModel = it }
            isCreate.let { this@CreateScreenFragment.isCreate = it }
        }
    }

    private fun initObsorvers() {
        with(viewModel) {
            isDataSaved.observe(viewLifecycleOwner) {
                if (it) {
                    requireContext().showToast("Saved")
                } else {
                    requireContext().showToast("Something went wrong")
                }
            }
        }
    }

    private fun showData(notate: UserNotateModel) {
        binding.etLogData.setText(notate.logData)
        binding.etPassData.setText(notate.privateInfo)
    }

    override fun onStop() {
        super.onStop()
        saveUserData()
    }

    private fun saveUserData() {
        val logData = binding.etLogData.text.toString()
        val passData = binding.etPassData.text.toString()
        viewModel.saveUserData(
            create = isCreate,
            userName = userName,
            logData = logData,
            passData = passData,
            userModel = userModel,
        )
    }
}