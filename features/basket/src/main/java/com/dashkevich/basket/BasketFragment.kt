package com.dashkevich.basket

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dashkevich.basket.adapter.BasketItemDelegate
import com.dashkevich.basket.adapter.basketAdapterDelegates
import com.dashkevich.basket.adapter.decor.BasketDecoration
import com.dashkevich.basket.databinding.FragmentBasketBinding
import com.dashkevich.util.common.AdapterItemDelegate
import com.dashkevich.util.getCurrentDate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class BasketFragment : Fragment(R.layout.fragment_basket) {

    lateinit var binding: FragmentBasketBinding
    private val basketViewModel: BasketViewModel by viewModel()
    private val basketAdapter = ListDelegationAdapter<List<AdapterItemDelegate>>(
        basketAdapterDelegates(
            itemClickedListener = {},
            onPlus = { id, price ->
                basketViewModel.addDish(id, price)
            },
            onMinus = { id ->
                basketViewModel.reduceDish(id)
            }
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBasketBinding.bind(view)
        binding.basketRv.addItemDecoration(BasketDecoration())
        binding.basketDate.text = getCurrentDate()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                basketViewModel.viewState.collect { basketModel ->
                    basketAdapter.items = basketModel.basketDishes.map { dish ->
                        BasketItemDelegate(dish, basketModel.dishesCount[dish.id]!!)
                    }
                    binding.basketRv.apply {
                        adapter = basketAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                    binding.basketBye.text = getString(com.dashkevich.ui.R.string.payment, basketModel.price)

                }
            }
        }
        basketViewModel.getProductInBasket()
    }

}