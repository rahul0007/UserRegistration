package com.assignment.userregistration.viewModel

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.assignment.userregistration.R
import com.assignment.userregistration.database.RoomRepository
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.lifecycle.ErrorModelView
import com.assignment.userregistration.lifecycle.SingleLiveData
import com.assignment.userregistration.utils.isValidPassword
import com.neostardemo.utils.SingleLiveEvent
import com.neostardemo.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    @ApplicationContext val context: Context,
) : ViewModel() {
    var delegatesUserDataInsert = SingleLiveData<String>()

    val errorLiveData: SingleLiveData<ErrorModelView> = SingleLiveData()
    var exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errorLiveData.postValue(
            ErrorModelView(
                messageTitle = throwable.message,
                tagException = throwable.printStackTrace().toString()
            )
        )
    }

    fun insertUserData(user: UserMaster) {
        roomRepository.insertUser(user)
    }

    fun getUsers() {
        getUserListDelegate.postValue(roomRepository.getUserList())
    }

    var getUserListDelegate = SingleLiveData<List<UserMaster>>()
    var imageUri = ObservableField<String>()


    var address = ObservableField<String>()
    var landmark = ObservableField<String>()
    var city = ObservableField<String>()
    var state = ObservableField<String>()
    var pinCode = ObservableField<String>()
    var qualification = ObservableField<String>()
    var yearOfPassing = ObservableField<String>()
    var grade = ObservableField<String>()
    var experience = ObservableField<String>()
    var designation = ObservableField<String>()
    var domain = ObservableField<String>()
    var gender = ObservableField(Const.MALE)
    private val _errorChannel = SingleLiveEvent<UiText>()
    val errorChannel: SingleLiveEvent<UiText> get() = _errorChannel


    var firstName = ObservableField<String>()
    var lastName = ObservableField<String>()
    var phoneNumber = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var confirmPassword = ObservableField<String>()


    //check the validation for register screen

    private fun registerValidate(): Boolean {
        when {
            firstName.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_first_name))
                return false
            }
            firstName.get().toString().length < 4 -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_invalid_first_name))
                return false
            }
            lastName.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_last_name))
                return false
            }
            lastName.get().toString().length < 4 -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_invalid_last_name))
                return false
            }
            phoneNumber.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_phone_number))
                return false
            }
            phoneNumber.get().toString().length < 10 -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_valid_phone_number))
                return false
            }
            email.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_email))
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email.get().toString()).matches() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_valid_email))
                return false
            }
            password.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_password))
                return false
            }
            !isValidPassword(password.get().toString()) -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_valid_password))
                return false
            }
            confirmPassword.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_confirm_password))
                return false
            }
            confirmPassword.get().toString() != password.get().toString() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.password_do_not_match))
                return false
            }
            else -> return true
        }
    }

    fun onClick(view: View) {
        if (registerValidate()) {
            view.findNavController().navigate(R.id.action_registerFragment_to_yourInfoFragment)
        }
    }

    // click to submit the user data
    fun onSubmitClick(view: View) {
        if (addressValidate()) {
            insertUserData(
                UserMaster(
                    id=0,
                    Profile=imageUri.get().toString(),
                    FirstName = firstName.get().toString(),
                    LastName= lastName.get().toString(),
                    PhoneNumber=phoneNumber.get().toString(),
                    Email=email.get().toString(),
                    Gender=gender.get().toString(),
                    Password=password.get().toString(),
                    Address= address.get().toString(),
                    Landmark=landmark.get().toString(),
                    City= city.get().toString(),
                    State=state.get().toString(),
                    PinCode=pinCode.get().toString(),
                    Qualification=qualification.get().toString(),
                    PassingYear=yearOfPassing.get().toString(),
                    Grade=grade.get().toString(),
                    Experience=experience.get().toString(),
                    Designation=designation.get().toString(),
                    Domain=domain.get().toString()
                )
            )
            Toast.makeText(view.context, "User data saved successfully.", Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_addressInfoFragment_to_userListFragment)
        }

    }


    //check the address validation in
    private fun addressValidate(): Boolean {
        when {
            address.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_address))
                return false
            }

            address.get().toString().length < 5 -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_valid_address))
                return false
            }
            landmark.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_landmark))
                return false
            }

            city.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_city))
                return false
            }

            state.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_state))
                return false
            }

            pinCode.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_pincode))
                return false
            }

            domain.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.validation_empty_domain))
                return false
            }


            else -> return true
        }
    }


    //click event next button in Your info screen
    fun onEduNextClick(view: View) {
        if (educationValidate()) {
            view.findNavController().navigate(R.id.action_yourInfoFragment_to_addressInfoFragment)
        }
    }

    //check the validation of education screen
    private fun educationValidate(): Boolean {
        when {
            qualification.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.empty_qualification))
                return false
            }
            yearOfPassing.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.blank_year_of_passing))
                return false
            }


            grade.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.blank_grade))
                return false
            }

            experience.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.blank_experience))
                return false
            }

            designation.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.blank_designation))
                return false
            }


            domain.get().isNullOrEmpty() -> {
                _errorChannel.postValue(UiText.StringResource(R.string.blank_domain))
                return false
            }


            else -> return true
        }
    }


}