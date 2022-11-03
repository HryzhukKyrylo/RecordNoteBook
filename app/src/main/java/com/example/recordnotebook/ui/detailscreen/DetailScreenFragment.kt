package com.example.recordnotebook.ui.detailscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.databinding.FragmentDetailScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment

class DetailScreenFragment : BaseFragment<FragmentDetailScreenBinding>() {

    private val args: DetailScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.userData?.let { data ->
            showUserData(data)
        }
    }

    private fun showUserData(testUserParams: UserNotateModel) {
        binding.tvDetailLog.text = testUserParams.logData
        binding.tvDetailPass.text = testUserParams.privateInfo
    }
}