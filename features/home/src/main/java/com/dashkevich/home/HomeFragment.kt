package com.dashkevich.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dashkevich.home.adapter.CategoryItemDelegate
import com.dashkevich.home.adapter.categoryAdapterDelegates
import com.dashkevich.home.adapter.decor.CategoryDecoration
import com.dashkevich.home.databinding.FragmentHomeBinding
import com.dashkevich.home.model.HomeModel
import com.dashkevich.util.AdapterItemDelegate
import com.dashkevich.util.OperationState
import com.dashkevich.util.stateHandler
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private val homeAdapter = ListDelegationAdapter<List<AdapterItemDelegate>>(
        categoryAdapterDelegates(itemClickedListener = { category ->
            val bundle = bundleOf("category_name" to category.name)
            findNavController().navigate(com.dashkevich.navigation.R.id.action_global_category, bundle)
        })
    )
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
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