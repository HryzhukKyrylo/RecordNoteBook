package com.example.recordnotebook.ui.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentMainScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>() {

    private val args: MainScreenFragmentArgs by navArgs()

    private val viewModel by viewModel<MainScreenViewModel>()
    private lateinit var recyclerUserNotate: RecyclerView
    private lateinit var adapter: MainScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData(userData = args.userData)
        initRecycler()
        initClickListener()
        initObservers()
    }

    private fun loadData(userData: LoginUserParams?) {
        if (userData != null) {
            viewModel.loadData(userData.loginParam)
        } else {
            Log.e("MainScreenFragment", "error: loadData() - no login data")
            requireContext().showToast("No login data!!")
        }
    }

    private fun initRecycler() {
        adapter = MainScreenAdapter() {
            viewModel.transitionToDetail(data = it)
        }
        recyclerUserNotate = binding.rvUserNotate
        recyclerUserNotate.adapter = adapter
    }

    private fun initObservers() {
        with(viewModel) {
            listUserNotates.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                    noDataVisible(true)
                } else {
                    noDataVisible(false)
                }
            }

            itemClicked.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(it)
                )
            }
            isTransitionToCreate.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(
                        MainScreenFragmentDirections
                            .actionMainScreenFragmentToCreateScreenFragment(args.userData?.loginParam,false)
                    )
                }
            }
        }
    }

    private fun noDataVisible(isVisible: Boolean) {
        binding.rvUserNotate.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvNoData.visibility = if (!isVisible) View.VISIBLE else View.GONE
    }

    private fun initClickListener() {
        binding.fabCreate.setOnClickListener {
            viewModel.transitionToCreate()
        }
    }
}