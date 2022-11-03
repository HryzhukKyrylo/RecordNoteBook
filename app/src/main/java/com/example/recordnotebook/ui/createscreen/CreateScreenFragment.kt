package com.example.recordnotebook.ui.createscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.domain.models.CreateUserParams
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.databinding.FragmentCreateScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateScreenFragment : BaseFragment<FragmentCreateScreenBinding>() {

    private val args: CreateScreenFragmentArgs by navArgs()
    private val viewModel by viewModel<CreateScreenViewModel>()
    private var userName: String? = null
    private var userModel: UserNotateModel? = null
    private var isCreate: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserName(args)
        userModel?.let {
            showData(it)
        }
    }

    private fun showData(notate: UserNotateModel) {
        binding.etLogData.setText(notate.logData)
        binding.etPassData.setText(notate.privateInfo)
    }

    private fun setUserName(args: CreateScreenFragmentArgs) {
        this.userName = args.userLogNameParam
        this.isCreate = args.isCreate
        this.userModel = args.userNotateModel
    }

    override fun onStop() {
        super.onStop()
        saveUserData()
    }

    private fun saveUserData() {
        if (!isCreate) {
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
        } else {
            userModel?.let { oldNotate ->
                val logData = binding.etLogData.text.toString()
                val passData = binding.etPassData.text.toString()
                val lastChange = System.currentTimeMillis()

                val userModel = CreateUserParams(
                    logName = oldNotate.userLogName,
                    title = null, //todo need implement
                    logData = logData,
                    privateInfo = passData,
                    createTimestamp = oldNotate.timeCreate,
                    lastTimestamp = lastChange,
                    isCreated = isCreate,

                    )
                viewModel.saveUserData(userModel)
            }
        }

    }
}