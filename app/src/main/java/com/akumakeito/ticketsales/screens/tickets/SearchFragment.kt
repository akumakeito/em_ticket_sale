package com.akumakeito.ticketsales.screens.tickets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.TicketsApp
import com.akumakeito.ticketsales.ViewModelFactory
import com.akumakeito.ticketsales.databinding.FragmentSearchBinding
import com.akumakeito.ticketsales.screens.adapters.OnInteractionListener
import com.akumakeito.ticketsales.screens.adapters.PopularDestinationAdapter
import com.akumakeito.ticketsales.util.CyrillicInputFilter
import com.akumakeito.ticketsales.util.PopularDestination
import com.akumakeito.ticketsales.util.hideKeyboard
import com.akumakeito.ticketsales.util.setInput
import com.akumakeito.ticketsales.util.updateHintVisibility
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

val destinationList = listOf(
    PopularDestination("Стамбул", R.drawable.image_istanbul),
    PopularDestination("Сочи", R.drawable.image_sochi),
    PopularDestination("Пхукет", R.drawable.image_phuket)
)


class SearchFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentSearchBinding is null")

    private lateinit var viewModel: TicketViewModel

    private lateinit var adapter: PopularDestinationAdapter

    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TicketsApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    companion object {
        const val TAG = "SearchModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[TicketViewModel::class.java]

        adapter = PopularDestinationAdapter(object : OnInteractionListener {
            override fun onClick(destination: PopularDestination) {
                viewModel.setToDest(destination.destination)
                binding.toEditText.setText(destination.destination)
            }
        })

        navController = findNavController()

        binding.rvPopularDestination.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheet: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!

        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.apply {
            adapter.submitList(destinationList)

            lifecycleScope.launch {
                viewModel.destination.collectLatest { dest ->

                    fromEditText.apply {
                        setInput(
                            saveInput = {
                                viewModel.setFromDest(fromEditText.text.toString())
                            },
                            updateHintVisibility = {
                                fromEditText.updateHintVisibility(
                                    fromInputContainer,
                                    R.string.from_hint,
                                    resources
                                )
                            }
                        )
                        setText(dest.fromDest)
                    }
                    toEditText.apply {
                        setInput(
                            saveInput = {
                                viewModel.setToDest(toEditText.text.toString())
                            },
                            updateHintVisibility = {
                                toEditText.updateHintVisibility(
                                    toInputContainer,
                                    R.string.to_hint,
                                    resources
                                )
                            }
                        )
                        setText(dest.toDest ?: "")

                    }
                }
            }

            fromEditText.apply {
                filters = arrayOf(CyrillicInputFilter())
                setOnFocusChangeListener { _, hasFocus ->
                    updateHintVisibility(fromInputContainer, R.string.from_hint, resources)
                    if (hasFocus) {
                        setSelection(text?.length ?: 0)
                    }
                }
            }

            toEditText.apply {
                filters = arrayOf(CyrillicInputFilter())

                setOnFocusChangeListener { _, hasFocus ->
                    updateHintVisibility(toInputContainer, R.string.to_hint, resources)

                    if (hasFocus) {
                        setSelection(text?.length ?: 0)
                    }
                }
                requestFocus()

                setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                    if (actionId == IME_ACTION_DONE) {
                        v.clearFocus()
                        hideKeyboard(v, requireContext())
                        if (v.text.toString().isNotEmpty()) {
                            navController.navigate(R.id.action_mainFragment_to_ticketsPreviewFragment)
                        }
                    }
                    return@OnEditorActionListener true
                })
            }

            complexRouteContainer.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_complexRouteFragment)
            }
            everywhereContainer.setOnClickListener {
                viewModel.setToDest(resources.getString(R.string.everywhere))
            }
            weekendsContainer.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_weekendsFragment)
            }
            hotSaleContainer.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_hotSalesFragment)
            }

            btnComplexRoute.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_complexRouteFragment)
            }
            btnEverywhere.setOnClickListener {
                viewModel.setToDest(resources.getString(R.string.everywhere))
            }
            btnWeekends.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_weekendsFragment)
            }
            btnHotSales.setOnClickListener {
                navController.navigate(R.id.action_mainFragment_to_hotSalesFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}