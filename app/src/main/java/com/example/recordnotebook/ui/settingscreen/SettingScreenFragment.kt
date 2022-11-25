package com.example.recordnotebook.ui.settingscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.FragmentSettingScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingScreenFragment : BaseFragment<FragmentSettingScreenBinding>() {

    private val viewModel: SettingScreenViewModel by viewModel()
    private var userData: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initObserver()
    }

    private fun showDeleteAccountDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.setting_screen_dialog_delete_account_title))
        builder.setMessage(getString(R.string.setting_screen_dialog_dialog_message))
        builder.setPositiveButton(
            getString(R.string.setting_screen_dialog_btn_yes)
        ) { _, _ ->
            viewModel.deleteAccount()
        }

        builder.setNegativeButton(getString(R.string.setting_screen_dialog_btn_no)) { _, _ ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun setUserData(data: String) {
        binding.tvUserLogName.text = data
    }

    private fun initObserver() {
        with(viewModel) {
            gotToRefactorAccount.observe(viewLifecycleOwner) {
                val action =
                    SettingScreenFragmentDirections.actionSettingScreenFragmentToAccountScreenFragment(
                    )
                findNavController().navigate(action)
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
            sessionName.observe(viewLifecycleOwner) { data ->
                if (data != null) {
                    setUserData(data)
                } else {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun initClickListener() {
        binding.btnDeleteAllData.setOnClickListener {
            showDeleteAccountDialog()
        }
        binding.ivRefactor.setOnClickListener {
            viewModel.goToRefactorAccount()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}