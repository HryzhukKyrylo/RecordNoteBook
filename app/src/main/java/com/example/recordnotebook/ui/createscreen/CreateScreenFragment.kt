package com.example.recordnotebook.ui.createscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.models.CreateUserParams
import com.example.recordnotebook.databinding.FragmentCreateScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateScreenFragment : BaseFragment<FragmentCreateScreenBinding>() {

    private val args: CreateScreenFragmentArgs by navArgs()
    private val viewModel by viewModel<CreateScreenViewModel>()
    private var userName: String? = null
    private var isCreate: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserName(args.userLogNameParam, args.isCreate)
    }

    private fun setUserName(userParams: String?, isCreate: Boolean) {
        this.userName = userParams
        this.isCreate = isCreate
    }

    override fun onStop() {
        super.onStop()
        saveUserData()
    }

    private fun saveUserData() {
        val logData = binding.etLogData.text.toString()
        val passData = binding.etPassData.text.toString()
        val timeCreated = System.currentTimeMillis()
        val lastChange = System.currentTimeMillis()
        val createUserParams = CreateUserParams(
            logName = userName,
            title = null, //todo need implement
            logData = logData,
            privateInfo = passData,
            createTimestamp = timeCreated,
            lastTimestamp = lastChange,
            isCreated = isCreate,
        )

        viewModel.saveUserData(createUserParams)
    }
}