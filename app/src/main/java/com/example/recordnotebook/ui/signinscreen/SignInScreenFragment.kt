package com.example.recordnotebook.ui.signinscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentSigninScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInScreenFragment : BaseFragment<FragmentSigninScreenBinding>() {

    private val arg: SignInScreenFragmentArgs by navArgs()

    private val viewModel by viewModel<SignInScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arg.logName
        data?.let {
            setLogData(it)
        }

        initClickListeners()
        initObservers()
    }

    private fun setLogData(data: String) {
        binding.etLogin.setText(data)
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
            viewModel.navigateToSignUpScreen()
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
                    val action =
                        SignInScreenFragmentDirections.actionSignInScreenFragmentToMainGraph()
                    findNavController().navigate(action)
                }
            }

            goToSignUpScreen.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController()
                        .navigate(
                            SignInScreenFragmentDirections
                                .actionSignInScreenFragmentToSignUpScreenFragment()
                        )
                }
            }
            showMessage.observe(viewLifecycleOwner) {
                requireContext().showToast(it)
            }
        }
    }
}