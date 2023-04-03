package com.assignment.userregistration.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment.userregistration.R
import com.assignment.userregistration.databinding.FragmentAddressInfoBinding
import com.assignment.userregistration.utils.showToast
import com.assignment.userregistration.viewModel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_toolbar.view.*

@AndroidEntryPoint
class AddressFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentAddressInfoBinding? = null
    private val viewModel: UserInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddressInfoBinding.inflate(inflater, container, false)
        binding.mainVM = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        clickEvents()
        setupObservers()
    }

    private fun clickEvents() {
        binding.include.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupViews() {
        // set title
        binding.include.textViewTitle.text = getString(R.string.your_address)

        //add the adapter and attach state list
        setAutoCompleteView(binding.editTextState, resources.getStringArray(R.array.states))


    }

    private fun setAutoCompleteView(
        editTextState: AppCompatAutoCompleteTextView?,
        stringArray: Array<String>
    ) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            stringArray
        )
        editTextState?.setAdapter(adapter)
        editTextState?.setOnTouchListener { paramView, paramMotionEvent ->
            editTextState.showDropDown()
            editTextState.requestFocus()
            false
        }
    }


    private fun setupObservers() {
        viewModel.errorChannel.observe(viewLifecycleOwner) {
            requireContext().showToast(it.asString(requireContext()))
        }
    }
}