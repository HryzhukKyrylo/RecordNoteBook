package com.example.recordnotebook.ui.accountscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.models.LoginUserModel
import com.example.recordnotebook.databinding.FragmentAccountScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountScreenFragment : BaseFragment<FragmentAccountScreenBinding>() {

    private val args: AccountScreenFragmentArgs by navArgs()
    private val viewModel by viewModel<AccountScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.userLogName?.let {
            requireContext().showToast(it)
            loadData(it)
        }

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
        }
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

    private fun loadData(data: String) {
        viewModel.loadData(data)
    }

}