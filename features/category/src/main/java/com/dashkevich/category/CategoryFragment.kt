package com.dashkevich.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dashkevich.category.databinding.FragmentCategoryBinding
import com.dashkevich.category.model.CategoryModel
import com.dashkevich.util.stateHandler
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var binding: FragmentCategoryBinding
    private val categoryViewModel: CategoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.viewState.collect { categoryModel ->
                    viewChanges(categoryModel)
                }
            }
        }
        categoryViewModel.getTags()
    }

    private fun viewChanges(categoryModel: CategoryModel) {
        categoryModel.tegsState.stateHandler(
            onError = {

            },
            onEmptyResult = {

            },
            onSuccess = {
                Log.i("CategoryFragment", categoryModel.tegs.toString())
            },
            onLoading = {

            }
        )
        categoryModel.dishesState.stateHandler(
            onError = {

            },
            onEmptyResult = {

            },
            onSuccess = {
                Log.i("CategoryFragment", categoryModel.dishes.toString())
            },
            onLoading = {

            }
        )

    }
}
