package com.example.recordnotebook.ui.signupscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentSignupScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpScreenFragment : BaseFragment<FragmentSignupScreenBinding>() {
    private val viewModel by viewModel<SignUpScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            isClearFields.observe(viewLifecycleOwner) {
                if (it) {
                    binding.etLogin.text = null
                    binding.etPassword.text = null
                }
            }
            isSavedSuccessful.observe(viewLifecycleOwner) {
                if (it) {
                    val data = binding.etLogin.text.toString()
                    val action = SignUpScreenFragmentDirections
                        .actionSignUpScreenFragmentToSigninScreenFragment(data)
                    findNavController().navigate(
                        action
                    )
                }
            }
            showMessage.observe(viewLifecycleOwner) {
                requireContext().showToast(it)
            }
        }
    }

    private fun initClickListeners() {
        with(binding) {
            btnSignUp.setOnClickListener {
                val login = etLogin.text.toString()
                val pass = etPassword.text.toString()
                val userParams = LoginUserParams(loginParam = login, passwordParam = pass)
                viewModel.saveLoginUser(userParams)
            }
            btnReset.setOnClickListener {
                viewModel.clearFields()
            }
        }
    }
}