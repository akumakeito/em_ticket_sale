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
import com.akumakeito.domain.state.AppState
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.TicketsApp
import com.akumakeito.ticketsales.ViewModelFactory
import com.akumakeito.ticketsales.databinding.FragmentMainBinding
import com.akumakeito.ticketsales.screens.adapters.MusicOfferAdapter
import com.akumakeito.ticketsales.util.CyrillicInputFilter
import com.akumakeito.ticketsales.util.HorizontalSpacingItemDecoration
import com.akumakeito.ticketsales.util.createToast
import com.akumakeito.ticketsales.util.hideKeyboard
import com.akumakeito.ticketsales.util.setInput
import com.akumakeito.ticketsales.util.updateHintVisibility
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Uninitialized binding")

    private lateinit var adapter: MusicOfferAdapter

    private lateinit var viewModel: TicketViewModel

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = MusicOfferAdapter(resources)
        binding.rvMusicOffers.adapter = adapter
        binding.rvMusicOffers.addItemDecoration(HorizontalSpacingItemDecoration(67))

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[TicketViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            lifecycleScope.launch {
                viewModel.musicOffersState.collectLatest { state ->
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
                    toEditText.setInput(
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
                }
            }




            fromEditText.apply {
                filters = arrayOf(CyrillicInputFilter())
                setOnFocusChangeListener { _, _ ->
                    updateHintVisibility(fromInputContainer, R.string.from_hint, resources)
                }
            }

            toEditText.apply {
                filters = arrayOf(CyrillicInputFilter())
                setOnFocusChangeListener { _, hasFocus ->
                    updateHintVisibility(toInputContainer, R.string.to_hint, resources)
                    if (hasFocus) {
                        val searchFragment = SearchFragment()
                        searchFragment.show(childFragmentManager, searchFragment.tag)
                    }
                }

                setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
                    if (actionId == IME_ACTION_DONE) {
                        v.clearFocus()
                        hideKeyboard(v, requireContext())
                        val searchFragment = SearchFragment()
                        searchFragment.show(childFragmentManager, searchFragment.tag)
                    }
                    return@OnEditorActionListener true
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}