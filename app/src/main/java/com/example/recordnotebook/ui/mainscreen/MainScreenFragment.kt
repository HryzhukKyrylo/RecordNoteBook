package com.example.recordnotebook.ui.mainscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.FragmentMainScreenBinding
import com.example.recordnotebook.databinding.NavHeaderMainBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.dp
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>() {

    private val viewModel by viewModel<MainScreenViewModel>()
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var recyclerUserNotate: RecyclerView
    private lateinit var adapter: MainScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDrawer()
        initRecycler()
        initClickListener()
        initObservers()
        iniBackPressed()
    }

    private fun showDialogDeleteAllNotate() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.main_screen_remove_notates))
        builder.setMessage(getString(R.string.main_screen_dialog_are_you_sure))
        builder.setPositiveButton(getString(R.string.main_screen_dialog_yes)) { _, _ ->
            viewModel.deleteAllUserNotate()
        }
        builder.setNegativeButton(getString(R.string.main_screen_dialog_no)) { _, _ ->
            //todo implement
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun iniBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START)
            } else {
                activity?.finish() //todo find another implement
            }
        }
    }

    private fun setUserDrawerData(userLogName: String?) {
        val bindingDrawer = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        userLogName?.let {
            bindingDrawer.tvHeaderName.text = it
        }
        bindingDrawer.itemHeaderMenuChangeTheme.setOnClickListener {
            viewModel.switchNightMode()
        }
    }

    private fun initDrawer() {
        mDrawerLayout = binding.drawerLayout
        val navView = binding.navView

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemMenuGoToSignInFragment -> {
                    val action = MainScreenFragmentDirections.actionToLogIn()
                    findNavController().navigate(action)
                }
                R.id.itemMenuGoToSettings -> {
                    val action =
                        MainScreenFragmentDirections.actionMainScreenFragmentToSettingScreenFragment(
                        )
                    findNavController().navigate(action)
                }
                R.id.itemMenuGoToAboutFragment -> {
                    val action =
                        MainScreenFragmentDirections.actionMainScreenFragmentToAboutScreenFragment()
                    findNavController().navigate(action)
                }
            }
            closeDrawer()
            return@setNavigationItemSelectedListener true
        }

        navView.setOnKeyListener { view, keyCode, keyEvent ->

            if (keyEvent.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                return@setOnKeyListener true
            }

            return@setOnKeyListener false
        }
    }

    private fun initRecycler() {
        adapter = MainScreenAdapter(
            clickListener = {
                viewModel.transitionToDetail(data = it)
            },
            longClickListener = { clickedItemModel, view ->
                MainMenu(this)
                    .addElement(
                        name = R.string.main_screen_delete,
                        listener = {
                            val oldList = ArrayList(adapter.currentList)
                            val index = oldList.indexOf(clickedItemModel)
                            oldList.removeAt(index)
                            adapter.submitList(oldList)
                            viewModel.removeNotate(clickedItemModel)
                        }
                    )
                    .addElement(
                        name = R.string.main_screen_refactor,
                        listener = {
                            viewModel.transitionToRefactor(itemModel = clickedItemModel)
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
            sessionData.observe(viewLifecycleOwner) { name ->
                if (name != null) {
                    loadData(name)
                    setUserDrawerData(name)
                }
            }
            listUserNotates.observe(viewLifecycleOwner) { listNotates ->
                if (listNotates.isNotEmpty()) {
                    adapter.submitList(listNotates)
                    noDataVisible(true)
                } else {
                    noDataVisible(false)
                }
            }
            clickedItemNotateModel.observe(viewLifecycleOwner) { userModel ->
                val action =
                    MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(
                        userModel
                    )
                findNavController().navigate(action)
            }
            isTransitionToCreate.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(
                        MainScreenFragmentDirections
                            .actionMainScreenFragmentToCreateScreenFragment(
                                false,
                                null
                            )
                    )
                }
            }
            itemToRefactor.observe(viewLifecycleOwner) { itemModel ->
                findNavController().navigate(
                    MainScreenFragmentDirections
                        .actionMainScreenFragmentToCreateScreenFragment(
                            true,
                            itemModel
                        )
                )
            }
            showMessage.observe(viewLifecycleOwner) {
                requireContext().showToast(it)
            }
            isNightMode.observe(viewLifecycleOwner) {
                setNightModeIcon(it)
            }
        }
    }

    private fun setNightModeIcon(isNightModeOn: Boolean) {
        val bindingHeader: NavHeaderMainBinding = NavHeaderMainBinding.bind(
            binding.navView.getHeaderView(0)
        )
        bindingHeader.itemHeaderMenuChangeTheme.setImageDrawable(
            if (isNightModeOn) ContextCompat.getDrawable(requireContext(), R.drawable.ic_sun)
            else ContextCompat.getDrawable(requireContext(), R.drawable.ic_moon)
        )
    }

    private fun noDataVisible(isVisible: Boolean) {
        binding.rvUserNotate.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvNoData.visibility = if (!isVisible) View.VISIBLE else View.GONE
    }

    private fun initClickListener() {
        binding.fabCreate.setOnClickListener {
            viewModel.transitionToCreate()
        }
        binding.burgerMenu.setOnClickListener {
            openDrawer()
        }
        binding.moreMenu.setOnClickListener {
            MainMenu(this).addElement(
                R.drawable.ic_baseline_delete_24,
                R.string.main_menu_delete_all
            ) {
                showDialogDeleteAllNotate()
            }
                .showAtRight(it)
        }
    }

    private fun openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT)
    }

    private fun closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START)

    }
}