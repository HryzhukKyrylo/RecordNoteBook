package com.example.recordnotebook.ui.detailscreen

import android.os.Bundle
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentDetailScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment

class DetailScreenFragment : BaseFragment<FragmentDetailScreenBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testUserParams = LoginUserParams(
            loginParam = "test1",
            passwordParam = "test2"
        )
        showUserData(testUserParams)
    }

    private fun showUserData(testUserParams: LoginUserParams) {
        binding.tvDetailLog.text = testUserParams.loginParam
        binding.tvDetailPass.text = testUserParams.passwordParam
    }

}