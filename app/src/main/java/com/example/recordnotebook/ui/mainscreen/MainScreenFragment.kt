package com.example.recordnotebook.ui.mainscreen

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.FragmentMainScreenBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.dp
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>() {

    private val args: MainScreenFragmentArgs by navArgs()

    private val viewModel by viewModel<MainScreenViewModel>()
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var recyclerUserNotate: RecyclerView
    private lateinit var adapter: MainScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData(args)
        initDrawer()
        initRecycler()
        initClickListener()
        initObservers()
    }

    private fun loadData(args: MainScreenFragmentArgs) {
        args.userLogName?.let { data ->
            viewModel.loadData(userLogName = data)
        }
    }

    private fun initDrawer() {
        mDrawerLayout = binding.drawerLayout
        val navView = binding.navView
        navView.setupWithNavController(findNavController())
        NavigationUI.setupWithNavController(navView, findNavController())
    }

    private fun initRecycler() {
        adapter = MainScreenAdapter(
            clickListener = {
                viewModel.transitionToDetail(data = it)
            },
            longClickListener = { item, view ->
                MainMenu(this)
                    .addElement(
                        name = R.string.main_screen_delete,
                        listener = {
                            val oldList = ArrayList(adapter.currentList)
                            val index = oldList.indexOf(item)
                            oldList.removeAt(index)
                            adapter.submitList(oldList)
                            viewModel.removeNotate(item)
                        }
                    )
                    .addElement(
                        name = R.string.main_screen_refactor,
                        listener = {
                            viewModel.transitionToRefactor(itemModel = item)
                        }
                    )

                    .showAtRight(view)
            }
        )
        recyclerUserNotate = binding.rvUserNotate
        recyclerUserNotate.adapter = adapter
        recyclerUserNotate.addItemDecoration(
            MainScreenItemDecoration(
                verticalSpace = (10.dp).toInt(),
                horizontalSpace = (8.dp).toInt()
            )
        )
    }

    private fun initObservers() {
        with(viewModel) {
            listUserNotates.observe(viewLifecycleOwner) { listNotates ->
                if (listNotates.isNotEmpty()) {
                    adapter.submitList(listNotates)
                    noDataVisible(true)
                } else {
                    noDataVisible(false)
                }
            }

            itemClicked.observe(viewLifecycleOwner) { userModel ->
                val action = MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(userModel)
                findNavController().navigate(action)
            }
            isTransitionToCreate.observe(viewLifecycleOwner) { userLogName ->
                findNavController().navigate(
                    MainScreenFragmentDirections
                        .actionMainScreenFragmentToCreateScreenFragment(
                            userLogName,
                            false,
                            null
                        )
                )
            }
            isDeleted.observe(viewLifecycleOwner) { isDeleted ->
                requireContext().showToast("Deleted - $isDeleted")
            }
            itemToRefactor.observe(viewLifecycleOwner) { itemModel ->
                findNavController().navigate(
                    MainScreenFragmentDirections
                        .actionMainScreenFragmentToCreateScreenFragment(
                            itemModel.userLogName,
                            true,
                            itemModel
                        )
                )
            }
        }
    }

    private fun noDataVisible(isVisible: Boolean) {
        binding.rvUserNotate.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvNoData.visibility = if (!isVisible) View.VISIBLE else View.GONE
    }

    private fun initClickListener() {
        binding.fabCreate.setOnClickListener {
            viewModel.transitionToCreate(args.userLogName)
        }
        binding.burgerMenu.setOnClickListener {
            mDrawerLayout.openDrawer(Gravity.LEFT)
        }
        binding.moreMenu.setOnClickListener {
            MainMenu(this).addElement(
                R.drawable.ic_baseline_delete_24,
                R.string.main_menu_delete_all
            ) {
                viewModel.deleteAllUserNotate()
            }
                .showAtRight(it)
        }
    }
}