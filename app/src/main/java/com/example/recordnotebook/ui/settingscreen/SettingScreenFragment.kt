package com.example.recordnotebook.ui.settingscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.FragmentSettingScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingScreenFragment : BaseFragment<FragmentSettingScreenBinding>() {

    private val args: SettingScreenFragmentArgs by navArgs()
    private val viewModel: SettingScreenViewModel by viewModel()
    private var userData: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = args.userLogName
        if (data == null) {
            findNavController().navigateUp()
        } else {
            setUserData(data)
        }
        initClickListener()
        initObserver()
    }

    private fun showDeleteAccountDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.setting_screen_dialog_delete_account_title))
        builder.setMessage(getString(R.string.setting_screen_dialog_dialog_message))
        builder.setPositiveButton(
            getString(R.string.setting_screen_dialog_btn_yes)
        ) { _, _ -> userData?.let { it1 -> viewModel.deleteAccount(it1) } }
        builder.setNegativeButton(getString(R.string.setting_screen_dialog_btn_no)) { _, _ ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setUserData(data: String) {
        userData = data
        binding.tvUserLogName.text = data
    }

    private fun initObserver() {
        with(viewModel) {
            gotToRefactorAccount.observe(viewLifecycleOwner) {
                //todo implement
//            val action = SettingScreenFragmentDirection.actionGoToRefactorAccountScreen
//            findNavController().navigate(action)
                requireContext().showToast("Sorry - not implemented")
            }
            goToLogIn.observe(viewLifecycleOwner) {
                if (it) {
                    val action = SettingScreenFragmentDirections.actionGoToLogin()
                    findNavController().navigate(action)
                }
            }
            showMessage.observe(viewLifecycleOwner) {
                requireContext().showToast(it)
            }
        }
    }

    private fun initClickListener() {
        binding.btnDeleteAllData.setOnClickListener {
            showDeleteAccountDialog()
        }
        binding.ivRefactor.setOnClickListener {
            // todo implement
            requireContext().showToast("Sorry - not implemented")
//            viewModel.goToRefactorAccount()
        }
    }
}