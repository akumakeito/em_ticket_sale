package com.akumakeito.ticketsales.screens.tickets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.akumakeito.domain.state.AppState
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.TicketsApp
import com.akumakeito.ticketsales.ViewModelFactory
import com.akumakeito.ticketsales.databinding.FragmentTicketsPreviewBinding
import com.akumakeito.ticketsales.screens.adapters.TicketPreviewAdapter
import com.akumakeito.ticketsales.util.CyrillicInputFilter
import com.akumakeito.ticketsales.util.createToast
import com.akumakeito.ticketsales.util.dayShortMonthDayFormatDate
import com.akumakeito.ticketsales.util.getDatePicker
import com.akumakeito.ticketsales.util.hideKeyboard
import com.akumakeito.ticketsales.util.setInput
import com.akumakeito.ticketsales.util.updateHintVisibility
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketsPreviewFragment : Fragment() {

    private var _binding: FragmentTicketsPreviewBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Uninitialized binding")

    private lateinit var viewModel: TicketViewModel
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TicketsApp).component
    }

    private lateinit var adapter: TicketPreviewAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsPreviewBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[TicketViewModel::class.java]
        adapter = TicketPreviewAdapter(resources)
        binding.rvTicketsPreviewList.adapter = adapter
        binding.rvTicketsPreviewList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.getTicketOffers()
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivBackIcon.setOnClickListener {
                navController.navigateUp()
            }

            lifecycleScope.launch {
                viewModel.destination.collectLatest {
                    fromEditText.setText(it.fromDest)
                    toEditText.setText(it.toDest)
                }
            }

            lifecycleScope.launch {
                viewModel.ticketOffersState.collectLatest { state ->
                    progressBar.isVisible = state is AppState.Loading

                    when (state) {
                        AppState.Error -> createToast(
                            requireContext(),
                            resources.getString(R.string.something_went_wrong)
                        )

                        AppState.Loading -> progressBar.isVisible = state is AppState.Loading
                        is AppState.Success -> adapter.submitList(state.data.take(3))
                    }
                }
            }

            fromInputContainer.setEndIconOnClickListener {
                viewModel.reverseDestinations()
            }

            fromEditText.apply {
                updateHintVisibility(fromInputContainer, R.string.from_hint, resources)
                filters = arrayOf(CyrillicInputFilter())
                setOnFocusChangeListener { _, hasFocus ->
                    updateHintVisibility(fromInputContainer, R.string.from_hint, resources)
                    if (hasFocus) {
                        setSelection(text?.length ?: 0)
                    }
                }
            }


            toInputContainer.setEndIconOnClickListener {
                viewModel.setToDest("")
            }

            toEditText.apply {
                updateHintVisibility(toInputContainer, R.string.to_hint, resources)
                filters = arrayOf(CyrillicInputFilter())
                setOnFocusChangeListener { _, hasFocus ->
                    updateHintVisibility(toInputContainer, R.string.to_hint, resources)
                    if (hasFocus) {
                        setSelection(text?.length ?: 0)
                    }
                }

                setInput(
                    { viewModel.setToDest(text.toString()) },
                    {}
                )

                setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                    if (actionId == IME_ACTION_DONE) {
                        v.clearFocus()
                        hideKeyboard(v, requireContext())
                    }
                    return@OnEditorActionListener true
                })
            }

            chipPeopleClass.text = getString(R.string.people_class, 1, "эконом")

            val departure = viewModel.departureDate.value
            val arrival = viewModel.arrivalDate.value
            chipDateStart.text = dayShortMonthDayFormatDate(departure)
            chipDateStart.setOnClickListener {
                val datePicker = getDatePicker(resources.getString(R.string.choose_date_from))
                datePicker.show(childFragmentManager, "tag")
                datePicker.addOnPositiveButtonClickListener {
                    val departureDate = datePicker.selection ?: departure
                    viewModel.setDepartureDate(departureDate)

                    chipDateStart.text = dayShortMonthDayFormatDate(departureDate)
                }
            }
            arrival?.let { chipDateBack.text = dayShortMonthDayFormatDate(it) }

            chipDateBack.setOnClickListener {
                val datePicker = getDatePicker(
                    resources.getString(R.string.choose_date_to),
                    viewModel.departureDate.value
                )
                datePicker.show(childFragmentManager, "tag")
                datePicker.addOnPositiveButtonClickListener {
                    val arrivalDate = datePicker.selection!!
                    viewModel.setArrivalDate(arrivalDate)
                    chipDateBack.text = dayShortMonthDayFormatDate(arrivalDate)
                    chipDateBack.chipIcon = null
                }
            }

            btnShowAllTickets.setOnClickListener {
                navController.navigate(R.id.action_ticketsPreviewFragment_to_ticketFeedFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}