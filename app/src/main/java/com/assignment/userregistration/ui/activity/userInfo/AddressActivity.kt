package com.assignment.userregistration.ui.activity.userInfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.extentions.manageLoading
import com.assignment.userregistration.ui.activity.home.HomeActivity
import com.assignment.userregistration.ui.base.BaseActivity
import com.assignment.userregistration.utils.Constant
import com.assignment.userregistration.viewModel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.custom_toolbar.*

@AndroidEntryPoint
class AddressActivity : BaseActivity(), View.OnClickListener {
    lateinit var userMaster: UserMaster
    lateinit var userInfoViewModel: UserInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListener()
    }

    override fun findFragmentPlaceHolder() = 0
    override fun findContentView() = R.layout.activity_address

    override fun observeViewModel() {
        textViewTitle.text = resources.getString(R.string.your_address)

        setAutoCompleteView(editTextState, resources.getStringArray(R.array.states))

        userMaster = intent.extras?.getParcelable(Constant.USER_DATA)!!

        userInfoViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]


        userInfoViewModel.delegatesUserDataInsert.observe(this) {
            HomeActivity.start(this)
        }

        userInfoViewModel.errorLiveData.observe(this) {
            it.messageTitle?.let { it1 ->
                Toast.makeText(this, it1, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setOnClickListener() {
        buttonSubmit.setOnClickListener(this)
        imageViewBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonSubmit -> {
                userMaster.Address = editTextAddress.text.toString()
                userMaster.Landmark = editTextLandMark.text.toString()
                userMaster.City = editTextCity.text.toString()
                userMaster.State = editTextState.text.toString()
                userMaster.PinCode = editTextpinCode.text.toString()
                userInfoViewModel.manageLoading(
                    this, this, userInfoViewModel.delegatesUserDataInsert,
                    userInfoViewModel.errorLiveData
                ).insertUserData(userMaster)
            }
            R.id.imageViewBack->
            {
                finish()
            }
        }
    }

    companion object {
        fun start(context: BaseActivity, bundle: Bundle) {
            val intent = Intent(context, AddressActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    private fun setAutoCompleteView(
        editTextState: AppCompatAutoCompleteTextView?,
        stringArray: Array<String>
    ) {
        val adapter = ArrayAdapter(
            this,
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
}
