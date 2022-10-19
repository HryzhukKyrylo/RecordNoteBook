package com.example.recordnotebook.ui.loginscreen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.recordnotebook.databinding.FragmentLoginScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment

class LoginScreenFragment : BaseFragment<FragmentLoginScreenBinding>() {

    private lateinit var viewModel: LoginScreenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginScreenViewModel::class.java)

        initClickListeners()
        initObservers()
    }

    private fun initClickListeners() {
        binding.btnReset.setOnClickListener {
            viewModel.clearFields()
        }

        binding.btnConfirm.setOnClickListener {
            //todo implement
        }
    }

    private fun initObservers() {
        viewModel.isClearFields.observe(viewLifecycleOwner) { isClear ->
            if (isClear) {
                binding.etLogin.text = null
                binding.etPassword.text = null
            }
        }
    }
}