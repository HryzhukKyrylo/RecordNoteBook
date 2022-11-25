package com.example.recordnotebook.ui.accountscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.domain.models.LoginUserModel
import com.example.recordnotebook.databinding.FragmentAccountScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountScreenFragment : BaseFragment<FragmentAccountScreenBinding>() {

    private val viewModel by viewModel<AccountScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListeners()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            showMessage.observe(viewLifecycleOwner) { message ->
                requireContext().showToast(message)
            }
            userLoginData.observe(viewLifecycleOwner) { loginData ->
                setLoginData(loginData)
            }
            sessionName.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    loadData(data)
                } else {
                    findNavController().navigateUp()
                }
            }
            isLogNameChanged.observe(viewLifecycleOwner) {
                if (it) {
                    clearLogNameEdit()
                }
            }
            isPasswordChanged.observe(viewLifecycleOwner) {
                if (it) {
                    clearPasswordFields()
                }
            }
        }
    }

    private fun clearPasswordFields() {
        binding.etCurrentPassword.text = null
        binding.etNewPassword.text = null
        binding.etConfirmPassword.text = null
    }

    private fun clearLogNameEdit() {
        binding.etNewLogName.text = null
    }

    private fun setLoginData(loginData: LoginUserModel?) {
        loginData?.let { data ->
            binding.tvAccountLogName.text = data.login
        }
    }

    private fun initClickListeners() {
        binding.btnSaveLogName.setOnClickListener {
            val data = binding.etNewLogName.text.toString()
            viewModel.changeUserLogName(data)
        }
        binding.btnSavePassword.setOnClickListener {
            val curPass = binding.etCurrentPassword.text.toString()
            val newPass = binding.etNewPassword.text.toString()
            val confPass = binding.etConfirmPassword.text.toString()
            viewModel.saveNewPassword(curPass, newPass, confPass)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}