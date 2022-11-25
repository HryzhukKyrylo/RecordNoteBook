package com.example.recordnotebook.ui.detailscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.databinding.FragmentDetailScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.copyToClipboard
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailScreenFragment : BaseFragment<FragmentDetailScreenBinding>() {

    private val args: DetailScreenFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.userModel?.let { showUserData(it) }
        initClickListener()
        initObserv()

    }

    private fun initObserv() {
        viewModel.selectUserModel.observe(viewLifecycleOwner) { userModel ->
            findNavController().navigate(
                DetailScreenFragmentDirections.actionDetailScreenFragmentToCreateScreenFragment(
                    true,
                    userModel
                )
            )
        }
    }

    private fun initClickListener() {
        binding.tvDetailLog.setOnClickListener {
            viewModel.transitionToCreate(args.userModel)
        }
        binding.tvDetailPass.setOnClickListener {
            viewModel.transitionToCreate(args.userModel)
        }

        binding.tvDetailLog.setOnLongClickListener {
            val textForCopy = binding.tvDetailLog.text.toString()
            requireContext().copyToClipboard(textForCopy)
            true
        }
        binding.tvDetailPass.setOnLongClickListener {
            val textForCopy = binding.tvDetailPass.text.toString()
            requireContext().copyToClipboard(textForCopy)
            true
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showUserData(testUserParams: UserNotateModel) {
        binding.tvDetailLog.text = testUserParams.logData
        binding.tvDetailPass.text = testUserParams.privateInfo
    }

}