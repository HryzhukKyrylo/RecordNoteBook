package com.example.recordnotebook.ui.mainscreen

import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.recordnotebook.R
import com.example.recordnotebook.databinding.FragmentMainScreenBinding
import com.example.recordnotebook.databinding.NavHeaderMainBinding
import com.example.recordnotebook.ui.base.BaseFragment
import com.example.recordnotebook.utils.dp
import com.example.recordnotebook.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>() {

    private val args: MainScreenFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val viewModel by viewModel<MainScreenViewModel>()
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var recyclerUserNotate: RecyclerView
    private lateinit var adapter: MainScreenAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData(args)
        setUserDrawerData(args.userLogName)
        initDrawer()
        initRecycler()
        initClickListener()
        initObservers()
        iniBackPressed()
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
            viewModel.toggleNightMode()
        }
    }

    private fun loadData(args: MainScreenFragmentArgs) {
        args.userLogName?.let { data ->
            viewModel.loadData(userLogName = data)
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
                        MainScreenFragmentDirections.actionMainScreenFragmentToSettingScreenFragment()
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
                val action =
                    MainScreenFragmentDirections.actionMainScreenFragmentToDetailScreenFragment(
                        userModel
                    )
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
            viewModel.transitionToCreate(args.userLogName)
        }
        binding.burgerMenu.setOnClickListener {
            openDrawer()
        }
        binding.moreMenu.setOnClickListener {
            MainMenu(this).addElement(
                R.drawable.ic_baseline_delete_24,
                R.string.main_menu_delete_all
            ) {
                args.userLogName?.let { data ->
                    viewModel.deleteAllUserNotate(data)
                }
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

    fun onBackPressed() {

    }

}