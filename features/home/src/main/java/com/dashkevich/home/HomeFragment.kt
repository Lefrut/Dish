package com.dashkevich.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dashkevich.home.adapter.CategoryItemDelegate
import com.dashkevich.home.adapter.categoryAdapterDelegates
import com.dashkevich.home.adapter.decor.CategoryDecoration
import com.dashkevich.home.databinding.FragmentHomeBinding
import com.dashkevich.home.model.HomeModel
import com.dashkevich.util.common.AdapterItemDelegate
import com.dashkevich.util.getCityName
import com.dashkevich.util.getCurrentDate
import com.dashkevich.util.stateHandler
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {


    private val homeViewModel: HomeViewModel by viewModel()
    private val homeAdapter = ListDelegationAdapter<List<AdapterItemDelegate>>(
        categoryAdapterDelegates(itemClickedListener = { category ->
            val bundle = bundleOf("category_name" to category.name)
            findNavController().navigate(
                com.dashkevich.navigation.R.id.action_global_category,
                bundle
            )
        })
    )
    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

//        binding.cityHome.text = getCityName(requireContext(), )


        binding.dateHome.text = getCurrentDate()

        with(binding.categoriesRv) {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(CategoryDecoration())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.viewState.collect { homeModel ->
                    viewChanges(homeModel)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewChanges(homeModel: HomeModel) {
        homeModel.categoriesState.stateHandler(
            onError = {
                binding.progressCircular.visibility = View.INVISIBLE
            },
            onEmptyResult = {
                binding.progressCircular.visibility = View.INVISIBLE
            },
            onSuccess = {
                binding.progressCircular.visibility = View.INVISIBLE
                homeAdapter.items = homeModel.categories.map {
                    CategoryItemDelegate(
                        it.id,
                        it.name,
                        it.imageUrl
                    )
                }
            },
            onLoading = {
                binding.progressCircular.visibility = View.VISIBLE
            }
        )
        homeAdapter.notifyDataSetChanged()
    }

}