package com.example.recordnotebook.ui.signupscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentSignupScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
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
                    Toast.makeText(requireContext(), "Saved successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Save don't successful ", Toast.LENGTH_SHORT)
                        .show()
                }
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