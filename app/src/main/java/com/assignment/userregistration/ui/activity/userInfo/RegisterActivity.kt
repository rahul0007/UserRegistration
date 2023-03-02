package com.assignment.userregistration.ui.activity.userInfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.exception.ApplicationException
import com.assignment.userregistration.ui.base.BaseActivity
import com.assignment.userregistration.utils.Constant
import com.assignment.userregistration.utils.ImagePicker
import com.assignment.userregistration.viewModel.UserInfoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.io.File


@AndroidEntryPoint
class RegisterActivity : BaseActivity(), View.OnClickListener {

    lateinit var loginViewModel: UserInfoViewModel
    var isPasswordVisible = false
    lateinit var userMaster: UserMaster
    private var filePath: String? = null
    private lateinit var imageFile: File
    internal lateinit var behavior: BottomSheetBehavior<View>
    private var mBottomSheetDialog: BottomSheetDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListener()
    }

    override fun findFragmentPlaceHolder() = 0
    override fun findContentView() = R.layout.activity_register

    override fun observeViewModel() {
        textViewTitle.text = resources.getString(R.string.register)

        loginViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
        loginViewModel.delegatesUserDataInsert.observe(this) {
            Log.e("hello", "text")
        }

        loginViewModel.errorLiveData.observe(this) {
            it.messageTitle?.let { it1 ->
                Toast.makeText(this, it1, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun setOnClickListener() {
        buttonNext.setOnClickListener(this)
        imageViewToggle.setOnClickListener(this)
        layoutProfile.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonNext -> {
                if (isValidate()) {
                    val selectedId: Int = radioGroupGender.checkedRadioButtonId
                    val rb = findViewById<View>(selectedId) as RadioButton
                    userMaster = UserMaster()
                    userMaster.FirstName = editTextFirstName.text.toString()
                    userMaster.LastName = editTextLastName.text.toString()
                    userMaster.PhoneNumber = editTextPhoneNumber.text.toString()
                    userMaster.Email = editTextEmail.text.toString()
                    userMaster.Gender = rb.text.toString()
                    userMaster.Profile = filePath.toString()
                    userMaster.Password = editTextPassword.text.toString()
                    val bundle = Bundle()
                    bundle.putParcelable(Constant.USER_DATA, userMaster)
                    UserInfoActivity.start(this, bundle)
                }

            }
            R.id.layoutProfile -> {
                val imagePicker = ImagePicker()
                imagePicker.setImagePickerResult(object : ImagePicker.ImagePickerResult {
                    override fun onResult(path: String) {
                        filePath = path
                        Uri.fromFile(File(path))
                        imageFile = File(path)
                        Glide.with(this@RegisterActivity).load(imageFile)
                            .apply(RequestOptions().circleCrop()).into(imageViewUserProfile)
                    }
                })
                imagePicker.show(supportFragmentManager, imagePicker::class.java.name)
            }
            R.id.imageViewToggle -> {
                if (!isPasswordVisible) {
                    editTextPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    imageViewToggle.setImageDrawable(resources.getDrawable(R.drawable.ic_custom_show))
                    isPasswordVisible = true
                } else {
                    editTextPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    imageViewToggle.setImageDrawable(resources.getDrawable(R.drawable.ic_custom_hide))
                    isPasswordVisible = false
                }
            }
        }
    }


    private fun isValidate(): Boolean {
        try {
            validator.submit(editTextFirstName).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_first_name))
                .checkMinDigits(4).errorMessage(getString(R.string.validation_invalid_first_name))
                .check()

            validator.submit(editTextLastName).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_last_name))
                .checkMinDigits(4).errorMessage(getString(R.string.validation_invalid_last_name))
                .check()

            validator.submit(editTextPhoneNumber).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_phone_number))
                .checkMinDigits(10).errorMessage(getString(R.string.validation_valid_phone_number))
                .check()

            validator.submit(editTextEmail).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_email))
                .checkValidEmail().errorMessage(getString(R.string.validation_valid_email))
                .check()

            validator.submit(editTextPassword).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_password))
                .checkValidPassword().errorMessage(getString(R.string.validation_valid_password))
                .check()

            validator.submit(editTextConfirmPassword).checkEmpty()
                .errorMessage(getString(R.string.validation_empty_confirm_password))
                .checkValidPassword()
                .errorMessage(getString(R.string.validation_valid_confirm_password))
                .check()

            if (editTextPassword.text.toString() != editTextConfirmPassword.text.toString()) {
                Toast.makeText(this, getString(R.string.password_do_not_match), Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            return true
        } catch (e: ApplicationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        return false
    }
}
