package com.akumakeito.ticketsales.screens.tickets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.akumakeito.domain.state.AppState
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.TicketsApp
import com.akumakeito.ticketsales.ViewModelFactory
import com.akumakeito.ticketsales.databinding.FragmentTicketFeedBinding
import com.akumakeito.ticketsales.screens.adapters.TicketAdapter
import com.akumakeito.ticketsales.util.VerticalSpacingItemDecoration
import com.akumakeito.ticketsales.util.createToast
import com.akumakeito.ticketsales.util.dayFullMonthDateFormat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketFeedFragment : Fragment() {

    private var _binding: FragmentTicketFeedBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Uninitialized binding")

    private lateinit var viewModel: TicketViewModel

    private lateinit var adapter: TicketAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as TicketsApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketFeedBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[TicketViewModel::class.java]
        adapter = TicketAdapter(resources)
        viewModel.getTickets()
        binding.rvTickets.adapter = adapter
        binding.rvTickets.addItemDecoration(VerticalSpacingItemDecoration(16))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleScope.launch {
                viewModel.ticketState.collectLatest { state ->
                    progressBar.isVisible = state is AppState.Loading

                    when (state) {
                        AppState.Error -> createToast(
                            requireContext(),
                            resources.getString(R.string.something_went_wrong)
                        )

                        AppState.Loading -> progressBar.isVisible = state is AppState.Loading
                        is AppState.Success -> adapter.submitList(state.data)
                    }

                }
            }

            arrowBack.setOnClickListener {
                findNavController().navigateUp()
            }

            tvDestinations.text = resources.getString(
                R.string.from_to,
                viewModel.destination.value.fromDest,
                viewModel.destination.value.toDest
            )
            val destinations =
                if (viewModel.arrivalDate.value != null) {
                    "${dayFullMonthDateFormat(viewModel.departureDate.value)} - ${
                        dayFullMonthDateFormat(
                            viewModel.arrivalDate.value!!
                        )
                    }"
                } else {
                    dayFullMonthDateFormat(viewModel.departureDate.value)
                }
            tvDatePassengers.text = resources.getString(
                R.string.date_pass,
                destinations,
                "1"
            )

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}