package com.assignment.userregistration.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.databinding.FragmentUserListBinding
import com.assignment.userregistration.ui.adapter.UsersAdapter
import com.assignment.userregistration.ui.dialog.UsersInfoDialog
import com.assignment.userregistration.viewModel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_toolbar.view.*

@AndroidEntryPoint
class UserListFragment : Fragment(), UsersAdapter.OnItemClickStatusListener {
    private  var userListAdapter: UsersAdapter?=null
    private val viewModel: UserInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        binding.userInfoVM = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        clickEvents()
    }

    private fun clickEvents() {
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_registerFragment)
        }
    }


    //initialize the adapter and attach with recyclerview
    private fun setupViews() {
        binding.include.textViewTitle.text = resources.getString(R.string.users)
        binding.include.imageViewBack.visibility=View.GONE
        userListAdapter=UsersAdapter(requireContext(),this)
        binding.recyclerViewUser.adapter=userListAdapter
    }

    private fun setupObservers() {
        viewModel.getUsers()
        viewModel.getUserListDelegate.observe(requireActivity()) {
            userListAdapter?.updateData(it as ArrayList<UserMaster>?)
            if (userListAdapter?.itemCount!! > 0) {
                binding.textViewPlaceHolder.visibility = View.GONE
                binding.recyclerViewUser.visibility = View.VISIBLE
            } else {
                binding.textViewPlaceHolder.visibility = View.VISIBLE
                binding.recyclerViewUser.visibility = View.GONE
            }
        }
    }

    override fun itemsClicked(categoryMasters: UserMaster) {
        val dialog = UsersInfoDialog()
        dialog.setUserInfo(categoryMasters)
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog)
        dialog.show(childFragmentManager, "userMaster")
    }
}