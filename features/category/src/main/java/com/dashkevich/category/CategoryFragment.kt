package com.dashkevich.category

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dashkevich.category.adapter.*
import com.dashkevich.category.adapter.decor.DishDecoration
import com.dashkevich.category.adapter.decor.TegDecoration
import com.dashkevich.category.databinding.FragmentCategoryBinding
import com.dashkevich.category.databinding.ProductDialogBinding
import com.dashkevich.category.model.CategoryModel
import com.dashkevich.util.AdapterItemDelegate
import com.dashkevich.util.stateHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var binding: FragmentCategoryBinding
    private val categoryViewModel: CategoryViewModel by viewModel()
    private val tabsAdapter = ListDelegationAdapter<List<AdapterItemDelegate>>(
        horizontalTegsAdapterDelegates(
            itemClickedListener = {},
            tegsAdapter = ListDelegationAdapter(
                tegAdapterDelegates(itemClickedListener = { teg ->
                    categoryViewModel.clickTeg(teg.teg)
                })
            ),
            decorations = listOf(
                TegDecoration()
            )
        )
    )
    private val categoryAdapter = ListDelegationAdapter<List<AdapterItemDelegate>>(
        gridDishAdapterDelegates(
            onClick = {},
            dishAdapter = ListDelegationAdapter(
                dishAdapterDelegates(itemClickedListener = {
                    showProductDialog(
                        title = it.name,
                        description = it.description,
                        price = it.price,
                        weight = it.weight,
                        imageUrl = it.imageUrl,
                        onClickHeat = {

                        },
                        onClickButton = {

                        }
                    )
                }),
            ),
            decorations = listOf(
                DishDecoration()
            )
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)
        binding.nameCategory.text = arguments?.getString("category_name") ?: ""

        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.viewState.collect { categoryModel ->
                    categoryModel.viewChanges()
                }
            }
        }
        //showProductDialog()
    }

    private fun CategoryModel.viewChanges() {
        val progressCircular = binding.progressCircular
        categoryAdapter.items = listOf()
        var gridDishesItems: List<GridDishItemDelegate> = emptyList()
        var horizontalTegsItems: List<HorizontalTegsItemDelegate> = emptyList()
        tegsState.stateHandler(
            onError = {

            },
            onEmptyResult = {
            },
            onSuccess = {
                horizontalTegsItems = listOf(
                    HorizontalTegsItemDelegate(tegs.map { TegItemDelegate(it.first, it.second) })
                )
                try {
                    categoryViewModel.getDishes(
                        categoryViewModel.viewState.value.tegs
                            .filter { it.second }
                            .map { it.first }
                    )
                } catch (_: Exception) {
                }
                tabsAdapter.items = horizontalTegsItems
                with(binding.tabsRv) {
                    adapter = tabsAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            },
            onLoading = {
            }
        )
        dishesState.stateHandler(
            onError = {
                progressCircular.visibility = View.VISIBLE

            },
            onEmptyResult = {
                progressCircular.visibility = View.VISIBLE
            },
            onSuccess = {
                gridDishesItems =
                    listOf(GridDishItemDelegate(dishes = dishes.map { it.toDishItemDelegate() }))
                progressCircular.visibility = View.INVISIBLE
                categoryAdapter.items = gridDishesItems
                binding.categoryRv.apply {
                    adapter = categoryAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            },
            onLoading = {
                progressCircular.visibility = View.VISIBLE
            }
        )
    }


    }
}
