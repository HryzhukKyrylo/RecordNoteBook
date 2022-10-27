package com.example.recordnotebook.ui.loginscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentLoginScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreenFragment : BaseFragment<FragmentLoginScreenBinding>() {

    private val viewModel by viewModel<LoginScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.btnReset.setOnClickListener {
            viewModel.clearFields()
        }

        binding.btnConfirm.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val pass = binding.etPassword.text.toString()
            val userParams = LoginUserParams(loginParam = login, passwordParam = pass)
            viewModel.verifyUserLogin(userParams)
        }

        binding.tvSignUp.setOnClickListener {
            //todo implement
        }
    }

    private fun initObservers() {
        with(viewModel) {
            isClearFields.observe(viewLifecycleOwner) { isClear ->
                if (isClear) {
                    binding.etLogin.text = null
                    binding.etPassword.text = null
                }
            }
            isVerifySuccess.observe(viewLifecycleOwner) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Yess!!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}