package com.example.recordnotebook.ui.accountscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.recordnotebook.databinding.FragmentAccountScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast

class AccountScreenFragment : BaseFragment<FragmentAccountScreenBinding>() {

    private val args: AccountScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.userLogName?.let {
            requireContext().showToast(it)
            //todo implement
        }
    }

}