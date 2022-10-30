package com.example.recordnotebook.ui.mainscreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.LoginUserParams
import com.example.recordnotebook.databinding.FragmentMainScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>() {

    private val viewModel by viewModel<MainScreenViewModel>()
    private lateinit var recyclerUserNotate: RecyclerView
    private lateinit var adapter: MainScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userParam = LoginUserParams("test1", "test2")
        viewModel.loadData(userParam)
        initRecycler()
        initClickListener()
        initObservers()
    }

    private fun initRecycler() {
        adapter = MainScreenAdapter() {
            viewModel.transitionToDetail(data = it)
        }
        recyclerUserNotate = binding.rvUserNotate
        recyclerUserNotate.adapter = adapter
    }

    private fun initObservers() {
        viewModel.listUserNotates.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                noDataVisible(true)
            } else {
                noDataVisible(false)
            }
        }

        viewModel.itemClicked.observe(viewLifecycleOwner) {
            findNavController().navigate(
                MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(it)
            )
        }
    }

    private fun noDataVisible(isVisible: Boolean) {
        binding.rvUserNotate.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvNoData.visibility = if (!isVisible) View.VISIBLE else View.GONE
    }

    private fun initClickListener() {
        //todo implement
    }
}