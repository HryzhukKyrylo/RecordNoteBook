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
        }
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
    }
}