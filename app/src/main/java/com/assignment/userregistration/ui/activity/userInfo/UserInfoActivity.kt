package com.assignment.userregistration.ui.activity.userInfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.exception.ApplicationException
import com.assignment.userregistration.ui.base.BaseActivity
import com.assignment.userregistration.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.your_info.*

@AndroidEntryPoint
class UserInfoActivity : BaseActivity(), View.OnClickListener {
    lateinit var userMaster: UserMaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListener()

    }

    private fun setAutoCompleteView(
        editTextEducation: AppCompatAutoCompleteTextView?,
        stringArray: Array<String>
    ) {
        val adapter = ArrayAdapter(
            this,
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


    override fun findFragmentPlaceHolder() = 0
    override fun findContentView() = R.layout.your_info

    override fun observeViewModel() {
        textViewTitle.text = resources.getString(R.string.your_info)

        userMaster = intent.extras?.getParcelable(Constant.USER_DATA)!!
        setAutoCompleteView(editTextEducation,resources.getStringArray(R.array.education))
        setAutoCompleteView(editTextPassingYear,resources.getStringArray(R.array.passingYears))
        setAutoCompleteView(editTextDesignation,resources.getStringArray(R.array.designation))
    }


    private fun setOnClickListener() {
        buttonNext.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            com.assignment.userregistration.R.id.buttonNext -> {
                if (isValidate()) {
                    userMaster.Qualification = editTextEducation.text.toString()
                    userMaster.PassingYear = editTextPassingYear.text.toString()
                    userMaster.Grade = editTextGrade.text.toString()
                    userMaster.Experience = editTextExperience.text.toString()
                    userMaster.Designation = editTextDesignation.text.toString()
                    userMaster.Domain = editTextDomain.text.toString()
                    val bundle = Bundle()
                    bundle.putParcelable(Constant.USER_DATA, userMaster)
                    AddressActivity.start(this, bundle)
                }

            }
        }
    }

    private fun isValidate(): Boolean {
        try {
            validator.submit(editTextEducation).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_education))
                .check()

            validator.submit(editTextPassingYear).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_passing_years))
                .check()

            validator.submit(editTextGrade).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_grade))
                .check()

            validator.submit(editTextExperience).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_experience))
                .check()

            validator.submit(editTextDesignation).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_designation))
                .check()

            validator.submit(editTextDomain).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_domain))
                .check()


            return true
        } catch (e: ApplicationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        return false
    }

    companion object {
        fun start(context: BaseActivity, bundle: Bundle) {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
