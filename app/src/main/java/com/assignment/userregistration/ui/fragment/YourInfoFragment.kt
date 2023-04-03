package com.assignment.userregistration.ui.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment.userregistration.R
import com.assignment.userregistration.databinding.FragmentYourInfoBinding
import com.assignment.userregistration.utils.showToast
import com.assignment.userregistration.viewModel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_toolbar.view.*

@AndroidEntryPoint
class YourInfoFragment : Fragment() {
    private var _binding: FragmentYourInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentYourInfoBinding.inflate(inflater, container, false)
        binding.mainVM = viewModel
        return binding.root
    }

    private fun setupObservers() {
        viewModel.errorChannel.observe(viewLifecycleOwner) {
            requireContext().showToast(it.asString(requireContext()))
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        clickEvents()
    }

    private fun clickEvents() {
        binding.toolbar.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupViews() {
        with(binding) {
            //setting the title
            toolbar.textViewTitle.text = getString(R.string.your_info)

            //setAutoCompleteView for education,passingYears,designation
            setAutoCompleteView(editTextEducation, resources.getStringArray(R.array.education))
            setAutoCompleteView(editTextPassingYear, resources.getStringArray(R.array.passingYears))
            setAutoCompleteView(editTextDesignation, resources.getStringArray(R.array.designation))
        }


    }


    private fun setAutoCompleteView(
        editTextEducation: AppCompatAutoCompleteTextView?,
        stringArray: Array<String>
    ) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            stringArray
        )
        editTextEducation?.setAdapter(adapter)
        editTextEducation?.setOnTouchListener { paramView, paramMotionEvent ->
            editTextEducation.showDropDown()
            editTextEducation.requestFocus()
            false
        }
    }

}